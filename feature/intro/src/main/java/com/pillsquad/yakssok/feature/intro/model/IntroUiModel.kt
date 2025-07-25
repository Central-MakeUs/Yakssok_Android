package com.pillsquad.yakssok.feature.intro.model

data class IntroUiModel(
    val isLoading: Boolean = true,
    val isHaveToSignup: Boolean = false,
    val isEnabled: Boolean = false,
    val nickName: String = "",
    val token: String = "",
    val loginSuccess: Boolean = false
)
