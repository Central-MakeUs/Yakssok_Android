package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MyInfoResponse(
    @SerialName("nickname")
    val nickName: String,

    @SerialName("profileImageUrl")
    val profileImage: String? = null,

    @SerialName("medicationCount")
    val medicationCount: Int,

    @SerialName("followingCount")
    val followingCount: Int
)
