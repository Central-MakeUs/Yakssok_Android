package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalTime

data class MyRoutine(
    val id: Int,
    val type: MedicationType,
    val name: String,
    val status: MedicationStatus,
    val intakeDays: List<WeekType>,
    val intakeCount: Int,
    val intakeTime: List<LocalTime>
)
