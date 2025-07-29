package com.pillsquad.yakssok.core.common

import kotlinx.datetime.LocalTime

fun LocalTime.formatToServerTime(): String {
    return "%02d:%02d:%02d".format(hour, minute, second)
}