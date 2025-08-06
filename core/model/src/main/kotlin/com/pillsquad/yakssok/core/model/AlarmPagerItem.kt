package com.pillsquad.yakssok.core.model

data class AlarmPagerItem (
    val notificationId: Int,
    val notificationType: String,
    val senderNickName: String,
    val senderProfileUrl: String,
    val receiverNickName: String,
    val receiverProfileUrl: String,
    val content: String,
    val createdAt: String,
    val isSentByMe: Boolean
)