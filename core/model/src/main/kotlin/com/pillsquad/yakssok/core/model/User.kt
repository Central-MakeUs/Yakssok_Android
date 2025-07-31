package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalDate

data class User (
    val id: Int,
    val nickName: String,
    val relationName: String,
    val profileImage: String,
    val notTakenCount: Int? = null,
    val routineCache: MutableMap<LocalDate, List<Routine>> = mutableMapOf(),
    val takenCache: MutableMap<LocalDate, Boolean> = mutableMapOf(),
    val isNotMedicine: Boolean = routineCache.isEmpty()
)