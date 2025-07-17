package com.pillsquad.yakssok.feature.mate.model

data class MateUiModel(
    val curPage: Int = 0,
    val mateId: Long = -1,
    val myCode: String = "abcdef1gh",
    val inputCode: String = "",
    val mateName: String = "김OO",
    val imgUrl: String = "",
    val mateNickName: String = "",
    val isEnabled: Boolean = false
)
