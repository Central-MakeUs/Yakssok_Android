package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalDate

data class UserCache(
    val userId: Int,
    val routineCache: MutableMap<LocalDate, List<Routine>>,
    val takenCache: MutableMap<LocalDate, Boolean>
)
