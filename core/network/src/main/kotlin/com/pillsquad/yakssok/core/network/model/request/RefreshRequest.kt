package com.pillsquad.yakssok.core.network.model.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RefreshRequest(
    val refreshToken: String
)
