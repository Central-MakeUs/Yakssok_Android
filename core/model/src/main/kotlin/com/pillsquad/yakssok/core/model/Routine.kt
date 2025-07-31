package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalTime


data class Routine(
    val routineId: Int?,
    val medicationName: String,
    val medicationType: MedicationType,
    val intakeTime: LocalTime,
    val isTaken: Boolean,
)