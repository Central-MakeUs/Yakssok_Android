package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RoutineResponse(
    val groupedSchedules: Map<String, List<ScheduleGroupDto>>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ScheduleGroupDto(
    val date: String,
    val allTaken: Boolean,
    val schedules: List<ScheduleDto>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ScheduleDto(
    val date: String,
    val scheduleId: Int?,
    val medicationType: String,
    val medicationName: String,
    val intakeTime: String,
    val isTaken: Boolean
)