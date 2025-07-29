package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.common.formatToServerTime
import com.pillsquad.yakssok.core.model.Medication
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest

internal fun Medication.toPostMedicationRequest(): PostMedicationRequest =
    PostMedicationRequest(
        name = name,
        medicineType = medicineType.name,
        startDate = startDate.toString(),
        endDate = endDate?.toString(),
        intakeDays = intakeDays.map { it.name },
        intakeCount = intakeCount,
        alarmSound = alarmSound.name,
        intakeTimes = intakeTimes.map { it.formatToServerTime() }
    )