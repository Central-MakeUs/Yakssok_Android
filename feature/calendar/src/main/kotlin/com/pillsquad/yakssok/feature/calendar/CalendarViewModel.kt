package com.pillsquad.yakssok.feature.calendar

import android.util.Log
import android.util.SparseArray
import androidx.core.util.size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserRoutineUseCase
import com.pillsquad.yakssok.core.domain.usecase.UpdateRoutineTakenUseCase
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.feature.calendar.model.CalendarCacheManager
import com.pillsquad.yakssok.feature.calendar.model.CalendarConfig
import com.pillsquad.yakssok.feature.calendar.model.CalendarUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getUserProfileListUseCase: GetUserProfileListUseCase,
    private val getUserRoutineUseCase: GetUserRoutineUseCase,
    private val updateRoutineTakenUseCase: UpdateRoutineTakenUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiModel())
    val uiState = _uiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val config = CalendarConfig()

    private val today: LocalDate
        get() = LocalDate.today()

    private val cacheManager
        get() = CalendarCacheManager(
            routineCache = uiState.value.routineCache,
            takenCache = uiState.value.takenCache
        )

    init {
        loadUserAndRoutines()
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }

    fun onMateClick(userIdx: Int) {
        _uiState.value = _uiState.value.copy(selectedUserIdx = userIdx)
    }

    fun onRoutineClick(routineId: Int) {
        val userIdx = uiState.value.selectedUserIdx
        val date = uiState.value.selectedDate
        val currentMap = uiState.value.routineCache[userIdx] ?: return

        if (userIdx != 0 || date != today) return

        val updated = currentMap[date]?.map {
            if (it.routineId == routineId) it.copy(isTaken = !it.isTaken) else it
        } ?: return

        val newUserMap = currentMap.toMutableMap().apply { put(date, updated) }
        val newCache = uiState.value.routineCache.copyAndPut(userIdx, newUserMap)

        _uiState.update { it.copy(routineCache = newCache) }

        viewModelScope.launch {
            updateRoutineTakenUseCase(routineId)
                .onFailure { _errorFlow.emit("네트워크 환경을 확인해주세요.") }
        }
    }

    fun loadUserAndRoutines() {
        viewModelScope.launch {
            getUserProfileListUseCase()
                .onSuccess { users ->
                    _uiState.value = _uiState.value.copy(userList = users)

                    val (startDate, endDate) = getStartEndDate()

                    loadUserRoutine(userId = null, userIdx = 0, startDate, endDate)

                    users.drop(1).forEachIndexed { index, friend ->
                        val userIdx = index + 1
                        loadUserRoutine(userId = friend.id, userIdx, startDate, endDate)
                    }
                }
                .onFailure {
                    Log.e("CalendarViewModel", "getUserProfileList failed", it)
                    _errorFlow.emit("네트워크 환경을 확인해주세요.")
                }
        }
    }

    fun loadDataForPage(page: Int) {
        val year = config.yearRange.first + (page / 12)
        val month = (page % 12) + 1

        val startDate = LocalDate(year, month, 1)
        val endDate = startDate.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)

        viewModelScope.launch {
            // 내 루틴
            loadUserRoutine(userId = null, userIdx = uiState.value.selectedUserIdx, startDate, endDate)

            // 친구 루틴
            uiState.value.userList.forEachIndexed { idx, user ->
                if (idx != uiState.value.selectedUserIdx) {
                    loadUserRoutine(user.id, idx, startDate, endDate)
                }
            }
        }
    }

    private suspend fun loadUserRoutine(
        userId: Int? = null,
        userIdx: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        val result = if (userId == null) {
            getUserRoutineUseCase(startDate, endDate)
        } else {
            getUserRoutineUseCase(userId = userId, startDate = startDate, endDate = endDate)
        }

        result.onSuccess { cache ->
            cacheManager.merge(userIdx, cache)
            updateUserListWithRoutineEmptyState(userIdx, cache)
        }.onFailure {
            Log.e("CalendarViewModel", "getRoutine failed for userId=$userId", it)
            _errorFlow.emit("네트워크 환경을 확인해주세요.")
        }
    }

    private fun updateUserListWithRoutineEmptyState(userIdx: Int, cache: UserCache) {
        _uiState.value = _uiState.value.copy(
            userList = uiState.value.userList.mapIndexed { idx, user ->
                if (idx == userIdx) {
                    user.copy(isNotMedicine = cache.routineCache.isEmpty())
                } else user
            }
        )
    }

    private fun getStartEndDate(): Pair<LocalDate, LocalDate> {
        val year = uiState.value.selectedDate.year
        val month = uiState.value.selectedDate.month.number

        val startDate = LocalDate(year, month, 1)
        val endDate = startDate.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)

        return startDate to endDate
    }

    private fun <V> SparseArray<V>.copyAndPut(key: Int, value: V): SparseArray<V> {
        return SparseArray<V>(this.size + 1).also { newArr ->
            for (i in 0 until this.size) newArr.put(this.keyAt(i), this.valueAt(i))
            newArr.put(key, value)
        }
    }
}