package com.pillsquad.yakssok.core.model

data class User (
    val id: Int,
    val nickName: String,
    val relationName: String,
    val profileImage: String,
    val notTakenCount: Int? = null,
    val isNotMedicine: Boolean = true
)