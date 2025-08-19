package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.AlarmPagerItem
import com.pillsquad.yakssok.core.model.MessageType
import com.pillsquad.yakssok.core.network.model.response.AlarmResponse
import kotlinx.datetime.LocalDateTime

internal fun AlarmResponse.toAlarmPagerItem(): AlarmPagerItem =
    AlarmPagerItem(
        notificationId = notificationId.toLong(),
        notificationType = notificationType.toMessageType(),
        senderNickName = senderNickName,
        senderProfileUrl = senderProfileUrl ?: "",
        receiverNickName = receiverNickName,
        receiverProfileUrl = receiverProfileUrl ?: "",
        content = content,
        createdAt = LocalDateTime.parse(createdAt),
        isSentByMe = isSentByMe
    )

internal fun String.toMessageType(): MessageType =
    try {
        MessageType.valueOf(this)
    } catch (e: Exception) {
        e.printStackTrace()
        MessageType.MEDICATION_TAKE
    }