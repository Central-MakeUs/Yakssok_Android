package com.pillsquad.yakssok.feature.routine.model

import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.model.Medication
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.WeekType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class RoutineUiModel(
    val curPage: Int = 0,
    val enabled: List<Boolean> = listOf(false, true, true),
    val pillName: String = "",
    val medicationType: MedicationType? = null,
    val startDate: LocalDate = LocalDate.today(),
    val endDate: LocalDate? = null,
    val intakeCount: Int = 1,
    val intakeDays: List<WeekType> = listOf(
        WeekType.MONDAY,
        WeekType.TUESDAY,
        WeekType.WEDNESDAY,
        WeekType.THURSDAY,
        WeekType.FRIDAY,
        WeekType.SATURDAY,
        WeekType.SUNDAY
    ),
    val intakeTimes: List<LocalTime> = listOf(
        LocalTime.now(),
        LocalTime.now(),
        LocalTime.now(),
    ),
    val alarmType: AlarmType = AlarmType.SCOLD
)

internal fun RoutineUiModel.toDomain(): Medication {
    val times = if (intakeTimes.size != intakeCount) {
        intakeTimes.take(intakeCount)
    } else {
        intakeTimes
    }

    return Medication(
        name = pillName,
        medicineType = medicationType ?: MedicationType.OTHER,
        startDate = startDate,
        endDate = endDate,
        intakeDays = intakeDays,
        intakeCount = intakeCount,
        alarmSound = alarmType,
        intakeTimes = times
    )
}