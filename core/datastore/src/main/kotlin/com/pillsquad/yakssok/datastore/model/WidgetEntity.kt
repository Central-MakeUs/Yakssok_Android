package com.pillsquad.yakssok.datastore.model

data class WidgetEntity(
    val sub: String,
    val progress: String,
    val nextRoutine: Int? = null,
)