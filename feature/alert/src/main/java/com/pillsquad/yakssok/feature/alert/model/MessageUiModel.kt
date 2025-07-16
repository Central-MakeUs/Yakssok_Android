package com.pillsquad.yakssok.feature.alert.model

import com.pillsquad.yakssok.core.model.MessageType

data class MessageUiModel(
    val name: String,
    val imgUrl: String,
    val message: String,
    val isMine: Boolean,
    val messageType: MessageType,
    val time: Int
)
