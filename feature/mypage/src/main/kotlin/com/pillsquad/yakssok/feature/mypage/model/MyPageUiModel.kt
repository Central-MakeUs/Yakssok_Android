package com.pillsquad.yakssok.feature.mypage.model

data class MyPageUiModel(
    val nickName: String,
    val profileImageUrl: String,
    val medicationCount: Int,
    val mateCount: Int,
    val isAgreement: Boolean
)
