package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AlarmListResponse(
    val hasNext: Boolean,
    val content: List<AlarmResponse>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AlarmResponse(
    val notificationId: Int,
    val notificationType: String,
    val senderNickName: String,
    val senderProfileUrl: String?,
    val receiverNickName: String,
    val receiverProfileUrl: String?,
    val content: String,
    val createdAt: String,
    val isSentByMe: Boolean
)