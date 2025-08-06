package com.pillsquad.yakssok.core.common

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalTime.Companion.now(): LocalTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
}

fun LocalTime.formatToServerTime(): String {
    return "%02d:%02d:%02d".format(hour, minute, second)
}

fun formatLocalTime(localTime: LocalTime): String {
    val javaTime = java.time.LocalTime.of(localTime.hour, localTime.minute)
    val formatter = DateTimeFormatter.ofPattern("h:mm a") // ex: 9:00 AM, 10:00 PM
    return javaTime.format(formatter)
}

fun formatKotlinxTime(time: LocalTime): String {
    val amPm = if (time.hour >= 12) "오후" else "오전"
    val timeFormatter = LocalTime.Format {
        amPmHour(); char(':'); minute()
    }

    return "$amPm ${time.format(timeFormatter)}"
}