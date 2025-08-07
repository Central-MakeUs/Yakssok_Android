package com.pillsquad.yakssok.feature.home

import android.util.Log
import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserRoutineUseCase
import com.pillsquad.yakssok.core.domain.usecase.UpdateRoutineTakenUseCase
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.feature.home.model.HomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import javax.inject.Inject
import androidx.core.util.size
import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.domain.usecase.PostFeedbackUseCase
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalTime

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileListUseCase: GetUserProfileListUseCase,
    private val getUserRoutineUseCase: GetUserRoutineUseCase,
    private val updateRoutineTakenUseCase: UpdateRoutineTakenUseCase,
    private val postFeedbackUseCase: PostFeedbackUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiModel())
    val uiState = _uiState.asStateFlow()

    private val _remindState = MutableStateFlow<List<Routine>?>(null)
    val remindState = _remindState.asStateFlow()

    private val today = LocalDate.today()
    private val now = LocalTime.now()

    init {
        loadUserAndRoutines()
    }

    fun onSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(
            selectedDate = date
        )
    }

    fun onMateClick(userId: Int) {
        _uiState.value = _uiState.value.copy(
            selectedUserIdx = userId
        )
    }

    fun onRoutineClick(routineId: Int) {
        val userIdx = uiState.value.selectedUserIdx
        val date = uiState.value.selectedDate

        val currentCache = uiState.value.routineCache
        val currentMap = currentCache[userIdx] ?: return
        val updatedRoutines = currentMap[date]?.map {
            if (it.routineId == routineId) it.copy(isTaken = !it.isTaken) else it
        } ?: return

        val newRoutineMap = currentMap.toMutableMap().apply {
            put(date, updatedRoutines)
        }

        val newCache = SparseArray<MutableMap<LocalDate, List<Routine>>>().apply {
            for (i in 0 until currentCache.size) {
                val key = currentCache.keyAt(i)
                val value = currentCache.valueAt(i)
                put(key, value)
            }
            put(userIdx, newRoutineMap)
        }

        _uiState.value = _uiState.value.copy(routineCache = newCache)

        viewModelScope.launch {
            updateRoutineTakenUseCase(routineId)
        }
    }

    fun postFeedback(userId: Int, message: String, type: String) {
        viewModelScope.launch {
            postFeedbackUseCase(userId, message, type)
            _uiState.update {
                val newList = it.userList.map { user ->
                    if (user.id == userId) {
                        user.copy(notTakenCount = null)
                    } else {
                        user
                    }
                }
                val showFeedBackSection = newList.any { target -> target.notTakenCount != null }

                it.copy(
                    userList = newList,
                    showFeedBackSection = showFeedBackSection
                )
            }
        }
    }

    private fun loadUserAndRoutines() {
        viewModelScope.launch {
            getUserProfileListUseCase()
                .onSuccess { users ->
                    _uiState.value = _uiState.value.copy(
                        userList = users,
                        showFeedBackSection = users.any { target -> target.notTakenCount != null }
                    )

                    val (startDate, endDate) = getStartEndDate()

                    getUserRoutineUseCase(
                        startDate = startDate,
                        endDate = endDate
                    ).onSuccess { cache ->
                        updateRemindState(cache.routineCache)
                        updateRoutineCache(0, cache)
                    }.onFailure {
                        it.printStackTrace()
                        Log.e("HomeViewModel", "getRoutineMy: $it")
                        // Todo: Toast
                    }

                    users.drop(1).forEach { friend ->
                        getUserRoutineUseCase(
                            userId = friend.id,
                            startDate = startDate,
                            endDate = endDate
                        ).onSuccess { cache ->
                            val userIdx = users.indexOfFirst { it.id == friend.id }
                            if (userIdx != -1) {
                                updateRoutineCache(userIdx, cache)
                            }
                        }.onFailure {
                            it.printStackTrace()
                            Log.e("HomeViewModel", "getRoutineFriend: $it")
                            // Todo: Toast
                        }
                    }
                }
                .onFailure {
                    // Todo: Toast
                    Log.e("HomeViewModel", "getUserProfileList: $it")
                }
        }
    }

    private fun updateRoutineCache(userIdx: Int, cache: UserCache) {
        val sparseArray = uiState.value.routineCache.apply { put(userIdx, cache.routineCache) }

        _uiState.value = _uiState.value.copy(
            routineCache = sparseArray,
            userList = uiState.value.userList.mapIndexed { idx, user ->
                if (idx == userIdx) {
                    user.copy(isNotMedicine = cache.routineCache.isEmpty())
                } else {
                    user
                }
            }
        )
    }

    private fun updateRemindState(routineCache: MutableMap<LocalDate, List<Routine>>) {
        val todayList = routineCache[today] ?: return
        val remindList = todayList.filter { (it.intakeTime > now) && !it.isTaken }

        if (remindList.isEmpty()) return
        _remindState.value = remindList
    }

    fun clearRemindState() {
        _remindState.value = null
    }

    private fun getStartEndDate(): Pair<LocalDate, LocalDate> {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        val sunday = monday.plus(6, DateTimeUnit.DAY)
        return monday to sunday
    }
}