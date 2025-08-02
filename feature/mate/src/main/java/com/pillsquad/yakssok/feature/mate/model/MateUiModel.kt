package com.pillsquad.yakssok.feature.mate.model

data class MateUiModel(
    val curPage: Int = 0,
    val myName: String = "나",
    val myCode: String = "abcdef1gh",
    val friendCode: String = "",
    val relationName: String = "",
    val friendNickName: String = "김OO",
    val friendImageUrl: String = "",
    val isEnabled: Boolean = false
)
