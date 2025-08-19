package com.pillsquad.yakssok.core.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
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
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    return javaTime.format(formatter)
}

fun formatKotlinxTime(time: LocalTime): String {
    val amPm = if (time.hour >= 12) "오후" else "오전"
    val timeFormatter = LocalTime.Format {
        amPmHour(); char(':'); minute()
    }

    return "$amPm ${time.format(timeFormatter)}"
}

@OptIn(ExperimentalTime::class)
fun getTimeDifferenceString(target: LocalDateTime): String {
    val timeZone = TimeZone.currentSystemDefault()
    val now = Clock.System.now()
    val targetInstant = target.toInstant(timeZone)

    val diffInMinutes = (now - targetInstant).inWholeMinutes

    println("$targetInstant, $diffInMinutes")
    return when {
        diffInMinutes < 1 -> "방금 전"
        diffInMinutes < 60 -> "${diffInMinutes}분 전"
        diffInMinutes < 60 * 24 -> "${diffInMinutes / 60}시간 전"
        else -> "${diffInMinutes / (60 * 24)}일 전"
    }
}