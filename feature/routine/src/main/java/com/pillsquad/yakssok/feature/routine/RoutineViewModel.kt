package com.pillsquad.yakssok.feature.routine

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiModel())
    val uiState = _uiState.asStateFlow()

    fun updateCurPage(newPage: Int) {
        _uiState.update {
            it.copy(curPage = newPage)
        }
    }

    fun updatePillName(name: String) {
        _uiState.update {
            it.copy(pillName = name)
        }
        updateEnabled()
    }

    fun updatePillType(type: MedicationType) {
        _uiState.update {
            it.copy(medicationType = type)
        }
        updateEnabled()
    }

    fun updateStartDate(date: LocalDate) {
        _uiState.update {
            it.copy(startDate = date)
        }
    }

    fun updateEndDate(date: LocalDate?) {
        _uiState.update {
            it.copy(endDate = date)
        }
    }

    fun updateIntakeCount(count: Int) {
        _uiState.update {
            it.copy(intakeCount = count)
        }
    }

    fun updateIntakeDays(days: List<WeekType>) {
        _uiState.update {
            it.copy(intakeDays = days)
        }
    }

    fun updateIntakeTime(time: LocalTime, index: Int) {
        _uiState.update {
            it.copy(intakeTimes = it.intakeTimes.mapIndexed { i, t ->
                if (i == index) time else t
            })
        }
    }

    fun updateAlarmType(type: AlarmType) {
        _uiState.update {
            it.copy(alarmType = type)
        }
    }

    fun updateEnabled() {
        val isFirstEnabled =
            uiState.value.medicationType != null && uiState.value.pillName.trim().isNotEmpty()
        _uiState.update {
            it.copy(
                enabled = listOf(isFirstEnabled, true, true)
            )
        }
    }
}