package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.AlarmPagerItem
import com.pillsquad.yakssok.core.network.model.response.AlarmResponse

internal fun AlarmResponse.toAlarmPagerItem(): AlarmPagerItem =
    AlarmPagerItem(
        notificationId = notificationId,
        notificationType = notificationType,
        senderNickName = senderNickName,
        senderProfileUrl = senderProfileUrl,
        receiverNickName = receiverNickName,
        receiverProfileUrl = receiverProfileUrl,
        content = content,
        createdAt = createdAt,
        isSentByMe = isSentByMe
    )