package com.pillsquad.yakssok.core.common

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
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