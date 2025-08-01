package com.pillsquad.yakssok.feature.myroutine.model

import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.model.PillProgressType
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.WeekType

data class PillUiModel(
    val pillName: String = "",
    val medicationType: MedicationType = MedicationType.OTHER,
    val pillProgressType: PillProgressType = PillProgressType.BEFORE,
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
    val intakeTimes: String = "",
    val alarmType: AlarmType = AlarmType.SCOLD
)
