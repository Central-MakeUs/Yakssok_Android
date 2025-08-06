package com.pillsquad.yakssok.core.network.model.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MyInfoRequest(
    val nickname: String,
    val profileImageUrl: String
)