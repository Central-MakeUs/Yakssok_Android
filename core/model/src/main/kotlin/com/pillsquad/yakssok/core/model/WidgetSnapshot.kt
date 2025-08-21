package com.pillsquad.yakssok.core.model

data class WidgetSnapshot(
    val rows: List<WidgetItem> = emptyList(),
    val progress: String = "0/0회"
)

data class WidgetItem(
    val routineId: Int,
    val intakeTime: String,
    val medicationName: String,
    val isTaken: Boolean
)