package com.pillsquad.yakssok.feature.routine.model

import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.feature.routine.util.now
import com.pillsquad.yakssok.feature.routine.util.today
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class RoutineUiModel(
    val curPage: Int = 0,
    val enabled: List<Boolean> = listOf(false, true, true),
    val pillName: String = "",
    val pillType: PillType? = null,
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
)
