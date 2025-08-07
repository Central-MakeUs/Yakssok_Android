package com.pillsquad.yakssok.feature.alert.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.common.getTimeDifferenceString
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MessageType
import com.pillsquad.yakssok.feature.alert.R
import kotlinx.datetime.LocalDateTime

@Composable
internal fun AlarmComponent(
    name: String,
    imgUrl: String,
    message: String,
    isMine: Boolean,
    messageType: MessageType,
    time: LocalDateTime
) {
    val bgColor = when (messageType) {
        MessageType.FEEDBACK_NAG -> YakssokTheme.color.primary400
        MessageType.FEEDBACK_PRAISE -> YakssokTheme.color.subBlue
        else -> YakssokTheme.color.grey50
    }
    val textColor = when (messageType) {
        MessageType.MEDICATION_TAKE -> YakssokTheme.color.grey950
        MessageType.MEDICATION_NOT_TAKEN -> YakssokTheme.color.grey950
        MessageType.MEDICATION_NOT_TAKEN_FOR_FRIEND -> YakssokTheme.color.grey950
        else -> YakssokTheme.color.grey50
    }
    val timeString = getTimeDifferenceString(time)

    MessageSection(
        messageType = messageType,
        name = name,
        imgUrl = imgUrl,
        message = message,
        timeString = timeString,
        isMine = isMine,
        bgColor = bgColor,
        textColor = textColor
    )
}

@Composable
private fun MessageSection(
    messageType: MessageType,
    name: String,
    imgUrl: String,
    message: String,
    timeString: String,
    isMine: Boolean,
    bgColor: Color,
    textColor: Color,
) {
    val imageSize = if (isMine) 20.dp else 32.dp
    val textStyle = if (isMine) YakssokTheme.typography.caption else YakssokTheme.typography.body2
    val isLogo = messageType == MessageType.MEDICATION_TAKE || messageType == MessageType.MEDICATION_NOT_TAKEN || messageType == MessageType.MEDICATION_NOT_TAKEN_FOR_FRIEND

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        Row(
            modifier = if (isMine) {
                Modifier.padding(end = 11.dp)
            } else {
                Modifier
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLogo) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp),
                    painter = painterResource(R.drawable.logo_profile),
                    contentDescription = "logo",
                    tint = Color.Unspecified
                )
            } else {
                YakssokImage(
                    modifier = Modifier.size(imageSize),
                    imageUrl = imgUrl,
                    contentDescription = "profile"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = name,
                style = textStyle,
                color = YakssokTheme.color.grey900
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            if (isMine) {
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = timeString,
                    style = YakssokTheme.typography.caption,
                    color = YakssokTheme.color.grey400
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            MessageBubble(
                modifier = if (isLogo) {
                    Modifier.weight(1f)
                } else {
                    Modifier.wrapContentWidth()
                },
                text = message,
                bgColor = bgColor,
                textColor = textColor,
                isMine = isMine
            )

            if (!isMine) {
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = timeString,
                    style = YakssokTheme.typography.caption,
                    color = YakssokTheme.color.grey400
                )
            }
        }
    }
}