package com.pillsquad.yakssok.feature.routine.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalDate.Companion.today(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

@OptIn(ExperimentalTime::class)
fun LocalTime.Companion.now(): LocalTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
}

fun formatKotlinxTime(time: LocalTime): String {
    val amPm = if (time.hour >= 12) "오후" else "오전"
    val timeFormatter = LocalTime.Format {
        amPmHour(); char(':'); minute()
    }

    return "$amPm ${time.format(timeFormatter)}"
}