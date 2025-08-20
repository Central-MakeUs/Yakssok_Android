package com.pillsquad.yakssok.core.model

data class WidgetSnapshot(
    val subTitle: String = "오늘은 없어요!",
    val progress: String = "0/0회",
    val nextRoutineId: Int? = null,
)
