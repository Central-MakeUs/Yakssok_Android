package com.pillsquad.yakssok.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserRoutineUseCase
import com.pillsquad.yakssok.core.domain.usecase.UpdateRoutineTakenUseCase
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileListUseCase: GetUserProfileListUseCase,
    private val getUserRoutineUseCase: GetUserRoutineUseCase,
    private val updateRoutineTakenUseCase: UpdateRoutineTakenUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiModel())
    val uiState = _uiState.asStateFlow()

    private val today = LocalDate.today()

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

        val routineMap = uiState.value.routineCache[userIdx] ?: return
        val routines = routineMap[date]?.map {
            if (it.routineId == routineId) it.copy(isTaken = !it.isTaken) else it
        } ?: return

        routineMap[date] = routines

        _uiState.value = _uiState.value.copy(
            routineCache = uiState.value.routineCache.apply { put(userIdx, routineMap) }
        )

        viewModelScope.launch {
            updateRoutineTakenUseCase(routineId)
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

    private fun getStartEndDate(): Pair<LocalDate, LocalDate> {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        val sunday = monday.plus(6, DateTimeUnit.DAY)
        return monday to sunday
    }
}