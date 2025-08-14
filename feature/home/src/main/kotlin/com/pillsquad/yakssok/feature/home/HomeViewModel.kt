package com.pillsquad.yakssok.feature.home

import android.util.SparseArray
import androidx.core.util.size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.common.today
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
    private val updateRoutineTakenUseCase: UpdateRoutineTakenUseCase,
    private val postFeedbackUseCase: PostFeedbackUseCase
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1, extraBufferCapacity = 1)

    private val today: LocalDate
        get() = LocalDate.today()
    private val now: LocalTime
        get() = LocalTime.now()

    init {
        viewModelScope.launch { refreshTrigger.emit(Unit) }

        viewModelScope.launch {
            val usersFlow: Flow<List<User>> = refreshTrigger
                .mapLatest { fetchUsersOrFallback() }
                .onEach { users ->
                    val show = users.any { it.notTakenCount != null }

                    val prev = _uiState.value as? HomeUiState.Success

                    _uiState.value = HomeUiState.Success(
                        showFeedBackSection = show,
                        isInit = prev?.isInit ?: true,
                        userList = users,
                        selectedDate = prev?.selectedDate ?: today,
                        selectedUserIdx = prev?.selectedUserIdx ?: 0,
                        routineCache = prev?.routineCache ?: SparseArray(),
                        remindList = prev?.remindList ?: emptyList(),
                    )
                }

            val routinesFlow: Flow<Pair<SparseArray<MutableMap<LocalDate, List<Routine>>>, List<User>>> =
                usersFlow.flatMapLatest { users ->
                    flow {
                        val (startDate, endDate) = getStartEndDate()
                        val cache = buildRoutineCache(users, startDate, endDate)
                        emit(cache to users)
                    }
                }

            routinesFlow
                .catch { _errorFlow.emit("네트워크 환경을 확인해주세요.") }
                .collect { (cache, users) ->
                    val base = when (val current = _uiState.value) {
                        is HomeUiState.Success -> current
                        HomeUiState.Loading -> HomeUiState.Success()
                    }

                    val updatedUsers = recomputeIsNotMedicine(users, cache)
                    val show = updatedUsers.any { it.notTakenCount != null }

                    val computedRemind = cache[0]?.get(today).orEmpty().filter { it.isFeedbackRoutine(now) }
                    val shouldShow = base.isInit && computedRemind.isNotEmpty()

                    _uiState.value = base.copy(
                        showFeedBackSection = show,
                        userList = updatedUsers,
                        routineCache = cache,
                        remindList = if (shouldShow) computedRemind else base.remindList,
                        selectedDate = base.selectedDate,
                        selectedUserIdx = base.selectedUserIdx
                    )
                }
        }
    }

    fun refresh() {
        viewModelScope.launch { refreshTrigger.emit(Unit) }
    }

    fun onSelectedDate(date: LocalDate) {
        val state = _uiState.value
        if (state is HomeUiState.Success) {
            _uiState.value = state.copy(selectedDate = date)
        }
    }

    fun onMateClick(userIdx: Int) {
        val state = _uiState.value
        if (state is HomeUiState.Success) {
            _uiState.value = state.copy(selectedUserIdx = userIdx)
        }
    }

    fun onRoutineClick(routineId: Int) {
        val state = _uiState.value
        if (state !is HomeUiState.Success) return

        val userIdx = state.selectedUserIdx
        val date = state.selectedDate
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
                    val state = _uiState.value
                    if (state is HomeUiState.Success) {
                        val newList = state.userList.map { user ->
                            if (user.id == userId) user.copy(notTakenCount = null) else user
                        }
                        _uiState.value = state.copy(
                            userList = newList,
                            showFeedBackSection = newList.any { it.notTakenCount != null }
                        )
                    }
                }.onFailure {
                    _errorFlow.emit("네트워크 환경을 확인해주세요.")
                }
        }
    }

    fun getFeedbackList(userId: Int): List<Routine> {
        val state = _uiState.value
        if (state !is HomeUiState.Success) return emptyList()
        val idx = state.userList.indexOfFirst { it.id == userId }

        val feedbackList = state.routineCache[idx]?.get(today).orEmpty()
        return feedbackList.filter { it.isFeedbackRoutine(now) }
    }

    fun clearRemindState() {
        val state = _uiState.value
        if (state is HomeUiState.Success) {
            _uiState.value = state.copy(isInit = false, remindList = emptyList())
        }
    }

    private suspend fun fetchUsersOrFallback(): List<User> {
        val prev = (uiState.value as? HomeUiState.Success)?.userList ?: emptyList()
        return getUserProfileListUseCase().getOrElse {
            _errorFlow.emit("네트워크 환경을 확인해주세요.")
            prev
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