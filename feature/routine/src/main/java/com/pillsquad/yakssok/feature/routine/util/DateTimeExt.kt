package com.pillsquad.yakssok.feature.routine.util

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

fun formatKotlinxTime(time: LocalTime): String {
    val amPm = if (time.hour >= 12) "오후" else "오전"
    val timeFormatter = LocalTime.Format {
        amPmHour(); char(':'); minute()
    }

    return "$amPm ${time.format(timeFormatter)}"
}