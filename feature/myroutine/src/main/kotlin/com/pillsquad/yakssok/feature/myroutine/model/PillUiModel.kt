package com.pillsquad.yakssok.feature.myroutine.model

import com.pillsquad.yakssok.core.common.formatKotlinxTime
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.MyRoutine
import com.pillsquad.yakssok.core.model.WeekType

data class RoutineUiModel(
    val pillList: List<PillUiModel> = listOf(),
    val optionalShowId: Int? = null,
    val routineEndId: Int? = null
)

data class PillUiModel(
    val id: Int = 0,
    val pillName: String = "",
    val medicationType: MedicationType = MedicationType.OTHER,
    val medicationStatus: MedicationStatus = MedicationStatus.PLANNED,
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
)

internal fun MyRoutine.toPillUiModel(): PillUiModel =
    PillUiModel(
        id = id,
        pillName = name,
        medicationType = type,
        medicationStatus = status,
        intakeCount = intakeCount,
        intakeDays = intakeDays,
        intakeTimes = intakeTime.joinToString(" / ") { formatKotlinxTime(it) },
    )