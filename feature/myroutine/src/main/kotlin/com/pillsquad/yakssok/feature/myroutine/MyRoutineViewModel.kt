package com.pillsquad.yakssok.feature.myroutine

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.core.model.PillProgressType
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.feature.myroutine.model.PillUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyRoutineViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(listOf<PillUiModel>())
    val uiState = _uiState.asStateFlow()

    val testList = listOf(
        PillUiModel(
            pillName = "아세트아미노펜",
            medicationType = MedicationType.OTHER,
            pillProgressType = PillProgressType.BEFORE,
            intakeCount = 1,
            intakeDays = listOf(WeekType.MONDAY, WeekType.TUESDAY, WeekType.WEDNESDAY, WeekType.THURSDAY, WeekType.FRIDAY, WeekType.SATURDAY, WeekType.SUNDAY),
            intakeTimes = "오전 8시",
        ),
        PillUiModel(
            pillName = "아아미노펜",
            medicationType = MedicationType.SUPPLEMENT,
            pillProgressType = PillProgressType.IN_PROGRESS,
            intakeCount = 1,
            intakeDays = listOf(WeekType.MONDAY, WeekType.TUESDAY, WeekType.WEDNESDAY, WeekType.THURSDAY, WeekType.FRIDAY, WeekType.SATURDAY, WeekType.SUNDAY),
            intakeTimes = "오전 8시",
        ),
        PillUiModel(
            pillName = "타이레놀",
            medicationType = MedicationType.TEMPORARY,
            pillProgressType = PillProgressType.AFTER,
            intakeCount = 1,
            intakeDays = listOf(WeekType.MONDAY, WeekType.TUESDAY, WeekType.WEDNESDAY, WeekType.THURSDAY, WeekType.FRIDAY, WeekType.SATURDAY, WeekType.SUNDAY),
            intakeTimes = "오전 8시",
        )
    )

    init {
        _uiState.value = testList
    }

    fun updateList(idx: Int) {
        when(idx) {
            0 -> {
                _uiState.value = testList
            }
            1 -> {
                _uiState.value = testList.filter { it.pillProgressType == PillProgressType.BEFORE }
            }
            2 -> {
                _uiState.value = testList.filter { it.pillProgressType == PillProgressType.IN_PROGRESS }
            }
            3 -> {
                _uiState.value = testList.filter { it.pillProgressType == PillProgressType.AFTER }
            }
        }
    }
}