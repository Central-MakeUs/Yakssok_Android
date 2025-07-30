package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FollowingsResponse(
    val followingInfoResponses: List<FollowingInfoResponse>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FollowingInfoResponse(
    val userId: Int,
    val relationName: String,
    val profileImageUrl: String?,
    val nickName: String
)
