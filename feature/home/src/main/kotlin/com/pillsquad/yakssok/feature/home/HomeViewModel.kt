package com.pillsquad.yakssok.feature.home

import android.util.SparseArray
import androidx.core.util.size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.usecase.GetFeedbackTargetUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserRoutineUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostFeedbackUseCase
import com.pillsquad.yakssok.core.domain.usecase.UpdateRoutineTakenUseCase
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.feature.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileListUseCase: GetUserProfileListUseCase,
    private val getUserRoutineUseCase: GetUserRoutineUseCase,
    private val getFeedbackTargetUseCase: GetFeedbackTargetUseCase,
    private val updateRoutineTakenUseCase: UpdateRoutineTakenUseCase,
    private val postFeedbackUseCase: PostFeedbackUseCase
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1, extraBufferCapacity = 1)

    private val today get() = LocalDate.today()
    private val now get() = LocalTime.now()

    init {
        viewModelScope.launch { refreshTrigger.emit(Unit) }

        viewModelScope.launch {
            refreshTrigger
                .mapLatest { loadHome() }
                .catch { _errorFlow.emit("네트워크 환경을 확인해주세요.") }
                .collect { _uiState.value = it }
        }
    }

    fun refresh() {
        viewModelScope.launch { refreshTrigger.emit(Unit) }
    }

    private suspend fun loadHome(): HomeUiState {
        val previous = _uiState.value as? HomeUiState.Success

        return supervisorScope {
            val users = getUserProfileListUseCase().getOrElse {
                _errorFlow.emit("메이트 목록을 불러올 수 없습니다.")
                previous?.userList ?: HomeUiState.Success().userList
            }

            val (start, end) = getStartEndDate()
            val routineDef = async { buildRoutineCache(users, start, end) }
            val targetsDef = async {
                getFeedbackTargetUseCase()
                    .getOrElse {
                        _errorFlow.emit("피드백 목록을 불러올 수 없습니다.")
                        emptyList()
                    }
            }

            val cache = routineDef.await()
            val targets = targetsDef.await()

            val adjustedUsers = recomputeIsNotMedicine(users, cache)
            val todayRemind = cache[0]?.get(today).orEmpty().filter { it.isFeedbackRoutine(now) }
            val shouldShowRemind = (previous?.isInit ?: true) && todayRemind.isNotEmpty()

            HomeUiState.Success(
                isInit = previous?.isInit ?: true,
                userList = adjustedUsers,
                selectedDate = previous?.selectedDate ?: today,
                selectedUserIdx = previous?.selectedUserIdx ?: 0,
                routineCache = cache,
                remindList = if (shouldShowRemind) todayRemind else (previous?.remindList
                    ?: emptyList()),
                feedbackTargetList = targets
            )
        }
    }

    fun onSelectedDate(date: LocalDate) {
        (_uiState.value as? HomeUiState.Success)?.let {
            _uiState.value = it.copy(selectedDate = date)
        }
    }

    fun onMateClick(userIdx: Int) {
        (_uiState.value as? HomeUiState.Success)?.let {
            _uiState.value = it.copy(selectedUserIdx = userIdx)
        }
    }

    fun onRoutineClick(routineId: Int) {
        val state = _uiState.value as? HomeUiState.Success ?: return
        val userIdx = state.selectedUserIdx
        val date = state.selectedDate
        if (userIdx != 0 || date != today) return

        val currentMap = state.routineCache[userIdx] ?: return
        val updated = currentMap[date]?.map {
            if (it.routineId == routineId) it.copy(isTaken = !it.isTaken) else it
        } ?: return

        val newUserMap = currentMap.toMutableMap().apply { put(date, updated) }
        val newCache = state.routineCache.copyAndPut(userIdx, newUserMap)

        _uiState.value = state.copy(routineCache = newCache)

        viewModelScope.launch {
            updateRoutineTakenUseCase(routineId)
                .onFailure { _errorFlow.emit("네트워크 환경을 확인해주세요.") }
        }
    }

    fun postFeedback(userId: Int, message: String, type: String) {
        viewModelScope.launch {
            postFeedbackUseCase(userId, message, type)
                .onSuccess {
                    (_uiState.value as? HomeUiState.Success)?.let { state ->
                        val newList = state.feedbackTargetList.filter { it.userId != userId }
                        _uiState.value = state.copy(
                            feedbackTargetList = newList,
                        )
                    }
                }.onFailure {
                    _errorFlow.emit("피드백 전송에 실패했습니다.")
                }
        }
    }

    fun clearRemindState() {
        (_uiState.value as? HomeUiState.Success)?.let {
            _uiState.value = it.copy(isInit = false, remindList = emptyList())
        }
    }

    private suspend fun buildRoutineCache(
        users: List<User>,
        startDate: LocalDate,
        endDate: LocalDate
    ): SparseArray<MutableMap<LocalDate, List<Routine>>> {
        var acc = SparseArray<MutableMap<LocalDate, List<Routine>>>()

        val my = fetchRoutineOrEmpty(userId = null, startDate, endDate)
        acc = acc.copyAndPut(0, my.routineCache)

        users.drop(1).forEach { friend ->
            val cache = fetchRoutineOrEmpty(friend.id, startDate, endDate)
            val userIdx = users.indexOfFirst { it.id == friend.id }
            if (userIdx != -1) acc = acc.copyAndPut(userIdx, cache.routineCache)
        }
        return acc
    }

    private suspend fun fetchRoutineOrEmpty(
        userId: Int?,
        startDate: LocalDate,
        endDate: LocalDate
    ): UserCache {
        return if (userId == null) {
            getUserRoutineUseCase(startDate, endDate)
                .getOrElse {
                    _errorFlow.emit("네트워크 환경을 확인해주세요.")
                    UserCache.empty()
                }
        } else {
            getUserRoutineUseCase(userId, startDate, endDate)
                .getOrElse {
                    _errorFlow.emit("네트워크 환경을 확인해주세요.")
                    UserCache.empty()
                }
        }
    }

    private fun recomputeIsNotMedicine(
        users: List<User>,
        cache: SparseArray<MutableMap<LocalDate, List<Routine>>>
    ): List<User> {
        return users.mapIndexed { idx, user ->
            val hasAny = cache[idx]?.values?.any { it.isNotEmpty() } == true
            user.copy(isNotMedicine = !hasAny)
        }
    }

    private fun getStartEndDate(): Pair<LocalDate, LocalDate> {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        val sunday = monday.plus(6, DateTimeUnit.DAY)
        return monday to sunday
    }

    private fun <V> SparseArray<V>.copyAndPut(key: Int, value: V): SparseArray<V> {
        return SparseArray<V>(this.size + 1).also { newArr ->
            for (i in 0 until this.size) newArr.put(this.keyAt(i), this.valueAt(i))
            newArr.put(key, value)
        }
    }
}