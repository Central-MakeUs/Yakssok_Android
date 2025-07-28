package com.pillsquad.yakssok.core.network.model.response

data class MedicationCardResponse(
    val medicationType: String,
    val medicineName: String,
    val medicationStatus: String,
    val intakeDays: List<String>,
    val intakeCount: Int,
    val intakeTimes: List<String>
)