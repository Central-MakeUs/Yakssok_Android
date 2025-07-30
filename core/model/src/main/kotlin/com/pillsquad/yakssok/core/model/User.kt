package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalDate

data class User (
    val id: Int,
    val nickName: String,
    val relationName: String,
    val profileImage: String,
    val notTakenCount: Int? = null,
    val medicineCache: MutableMap<LocalDate, List<Medicine>> = mutableMapOf(),
    val isNotMedicine: Boolean = medicineCache.isEmpty()
)