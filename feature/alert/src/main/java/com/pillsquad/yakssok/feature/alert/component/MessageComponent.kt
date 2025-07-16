package com.pillsquad.yakssok.feature.alert.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MessageType

@Composable
private fun MessageComponent(
    name: String,
    imgUrl: String,
    message: String,
    isMine: Boolean,
    messageType: MessageType,
    time: Int
) {
    val bgColor = when(messageType) {
        MessageType.ALARM -> YakssokTheme.color.grey50
        MessageType.SNAGGING -> YakssokTheme.color.subBlue
        MessageType.PRAISE -> YakssokTheme.color.primary400
    }
    val textColor = when(messageType) {
        MessageType.ALARM -> YakssokTheme.color.grey950
        else -> YakssokTheme.color.grey50
    }
    val modifier = when(messageType) {
        MessageType.ALARM -> Modifier.width(283.dp)
        else -> Modifier
    }

    MessageSection(
        modifier = modifier,
        name = name,
        imgUrl = imgUrl,
        message = message,
        time = time,
        isMine = isMine,
        bgColor = bgColor,
        textColor = textColor
    )
}

@Composable
private fun MessageSection(
    modifier: Modifier,
    name: String,
    imgUrl: String,
    message: String,
    time: Int,
    isMine: Boolean,
    bgColor: Color,
    textColor: Color,
) {
    val imageSize = if (isMine) 20.dp else 32.dp
    val textStyle = if (isMine) YakssokTheme.typography.caption else YakssokTheme.typography.body2
    val alignment = if (isMine) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isMine) {
            Text(
                text = "${time}시간전",
                style = YakssokTheme.typography.caption,
                color = YakssokTheme.color.grey400
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Column(
            modifier = modifier,
            horizontalAlignment = alignment
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                YakssokImage(
                    modifier = Modifier.size(imageSize),
                    imageUrl = imgUrl,
                    contentDescription = "profile"
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = name,
                    style = textStyle,
                    color = YakssokTheme.color.grey900
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            MessageBubble(
                text = message,
                bgColor = bgColor,
                textColor = textColor
            )
        }

        if (!isMine) {
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "${time}시간전",
                style = YakssokTheme.typography.caption,
                color = YakssokTheme.color.grey400
            )
        }
    }
}