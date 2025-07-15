package com.pillsquad.yakssok.feature.routine.model

import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.model.WeekType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class RoutineUiModel(
    val curPage: Int = 0,
    val enabled: List<Boolean> = listOf(false, true, true),
    val pillName: String = "",
    val pillType: PillType? = null,
    val startDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
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
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    ),
)
