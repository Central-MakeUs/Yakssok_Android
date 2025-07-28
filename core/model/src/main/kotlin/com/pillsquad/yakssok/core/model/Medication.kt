package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Medication(
    val name: String,
    val medicineType: MedicationType,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val intakeDays: List<WeekType>,
    val intakeCount: Int,
    val alarmSound: AlarmType,
    val intakeTimes: List<LocalTime>
)
