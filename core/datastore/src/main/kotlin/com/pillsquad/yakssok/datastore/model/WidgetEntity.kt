package com.pillsquad.yakssok.datastore.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WidgetEntity(
    val rows: List<WidgetRow> = emptyList(),
    val progress: String = "0/0회"
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WidgetRow(
    val routineId: Int,
    val intakeTime: String,
    val medicationName: String,
    val isTaken: Boolean
)