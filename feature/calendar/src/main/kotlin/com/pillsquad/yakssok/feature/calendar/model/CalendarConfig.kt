package com.pillsquad.yakssok.feature.calendar.model

import kotlinx.datetime.DayOfWeek
import java.util.Locale

data class CalendarConfig(
    val yearRange: IntRange = 1970..2100,
    val locale: Locale = Locale.getDefault(),
    val startOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    val highlightToday: Boolean = true,
    val showWeekDayLabels: Boolean = true,
)
