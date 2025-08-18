package com.pillsquad.yakssok.core.model

data class Login(
    val isInitialized: Boolean,
    val accessToken: String,
    val refreshToken: String
)
