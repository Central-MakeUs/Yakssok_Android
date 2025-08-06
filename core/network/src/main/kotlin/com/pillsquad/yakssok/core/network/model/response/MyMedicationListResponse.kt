package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MyMedicationListResponse(
    val medicationCardResponses: List<MedicationCardResponse>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MedicationCardResponse(
    val medicationId: Int,
    val medicationType: String,
    val medicineName: String,
    val medicationStatus: String,
    val intakeDays: List<String>,
    val intakeCount: Int,
    val intakeTimes: List<String>
)