package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.common.formatToServerTime
import com.pillsquad.yakssok.core.model.Medication
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.MyRoutine
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest
import com.pillsquad.yakssok.core.network.model.response.MedicationCardResponse
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime

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

internal fun String.toMedicationType(): MedicationType =
    try {
        MedicationType.valueOf(this)
    } catch (e: Exception) {
        e.printStackTrace()
        MedicationType.OTHER
    }

internal fun String.toMedicationStatus(): MedicationStatus =
    try {
        MedicationStatus.valueOf(this)
    } catch (e: Exception) {
        e.printStackTrace()
        MedicationStatus.PLANNED
    }

internal fun String.toWeekType(): WeekType =
    try {
        WeekType.valueOf(this)
    } catch (e: Exception) {
        e.printStackTrace()
        WeekType.MONDAY
    }

internal fun MedicationCardResponse.toMyRoutine(): MyRoutine =
    MyRoutine(
        id = medicationId,
        type = medicationType.toMedicationType(),
        name = medicineName,
        status = medicationStatus.toMedicationStatus(),
        intakeDays = intakeDays.map { it.toWeekType() },
        intakeCount = intakeCount,
        intakeTime = intakeTimes.map { LocalTime.parse(it) }
    )
