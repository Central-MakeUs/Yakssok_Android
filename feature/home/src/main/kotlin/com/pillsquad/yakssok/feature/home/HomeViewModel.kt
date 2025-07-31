package com.pillsquad.yakssok.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserRoutineUseCase
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
    private val getUserRoutineUseCase: GetUserRoutineUseCase
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

    private fun loadUserAndRoutines() {
        viewModelScope.launch {
            getUserProfileListUseCase()
                .onSuccess { users ->
                    _uiState.value = _uiState.value.copy(
                        userList = users,
                        showFeedBackSection = users.any { target -> target.notTakenCount != null }
                    )

                    val myUser = users.firstOrNull() ?: return@onSuccess
                    val (startDate, endDate) = getStartEndDate()

                    getUserRoutineUseCase(
                        startDate = startDate,
                        endDate = endDate
                    ).onSuccess {  cache ->
                        updateUserCache(myUser.id, cache)
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
                            updateUserCache(friend.id, cache)
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

    private fun updateUserCache(userId: Int, cache: UserCache) {
        val updatedList = _uiState.value.userList.map { user ->
            if (user.id == userId) {
                user.copy(
                    routineCache = cache.routineCache,
                    takenCache = cache.takenCache
                )
            } else user
        }
        _uiState.value = _uiState.value.copy(userList = updatedList)
    }

    private fun getStartEndDate(): Pair<LocalDate, LocalDate> {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        val sunday = monday.plus(6, DateTimeUnit.DAY)
        return monday to sunday
    }
}