package com.pillsquad.yakssok.core.model

import java.time.LocalTime


data class Medicine(
    val name: String,
    val time: LocalTime,
    val isTaken: Boolean,
)
