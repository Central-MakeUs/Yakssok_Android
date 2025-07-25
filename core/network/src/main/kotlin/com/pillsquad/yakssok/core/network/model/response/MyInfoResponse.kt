package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MyInfoResponse(
    val nickName: String,
    val profileImage: String,
    val medicationCount: Int,
    val followingCount: Int
)
