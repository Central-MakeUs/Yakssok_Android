package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.Medication
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.core.network.model.request.IntakeDay
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest

internal fun Medication.toPostMedicationRequest(): PostMedicationRequest =
    PostMedicationRequest(
        name = name,
        medicineType = medicineType.name,
        startDate = startDate.toString(),
        endDate = endDate?.toString(),
        intakeDays = intakeDays.map { it.toIntakeDay() },
        intakeCount = intakeCount,
        alarmSound = alarmSound.name,
        intakeTimes = intakeTimes.map { it.toString() }
    )

internal fun WeekType.toIntakeDay(): IntakeDay =
    when(this) {
        WeekType.MONDAY -> IntakeDay.MONDAY
        WeekType.TUESDAY -> IntakeDay.TUESDAY
        WeekType.WEDNESDAY -> IntakeDay.WEDNESDAY
        WeekType.THURSDAY -> IntakeDay.THURSDAY
        WeekType.FRIDAY -> IntakeDay.FRIDAY
        WeekType.SATURDAY -> IntakeDay.SATURDAY
        WeekType.SUNDAY -> IntakeDay.SUNDAY
    }