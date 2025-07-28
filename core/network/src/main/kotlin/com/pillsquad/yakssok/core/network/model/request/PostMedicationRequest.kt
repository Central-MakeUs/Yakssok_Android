package com.pillsquad.yakssok.core.network.model.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PostMedicationRequest(
    val name: String,
    val medicineType: String,
    val startDate: String,
    val endDate: String?,
    val intakeDays: List<IntakeDay>,
    val intakeCount: Int,
    val alarmSound: String,
    val intakeTimes: List<String>
)

@Serializable
enum class IntakeDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}