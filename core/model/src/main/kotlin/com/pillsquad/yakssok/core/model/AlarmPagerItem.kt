package com.pillsquad.yakssok.core.model

import kotlinx.datetime.LocalDateTime

data class AlarmPagerItem (
    val notificationId: Long,
    val notificationType: MessageType,
    val senderNickName: String,
    val senderProfileUrl: String,
    val receiverNickName: String,
    val receiverProfileUrl: String,
    val content: String,
    val createdAt: LocalDateTime,
    val isSentByMe: Boolean
)