package com.pillsquad.yakssok.feature.intro.model

data class IntroUiModel(
    val isLoading: Boolean = true,
    val isLogin: Boolean = false,
    val isEnabled: Boolean = false,
    val nickName: String = ""
)
