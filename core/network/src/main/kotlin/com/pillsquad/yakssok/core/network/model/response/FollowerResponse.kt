package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FollowerResponse(
    val followerInfoResponses: List<FollowerInfoResponse>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FollowerInfoResponse(
    val userId: Int,
    val profileImageUrl: String?,
    val nickName: String
)
