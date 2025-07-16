package com.pillsquad.yakssok.feature.alert.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.alert.util.LeftMessageBubbleShape
import com.pillsquad.yakssok.feature.alert.util.RightMessageBubbleShape

@Composable
internal fun MessageBubble(
    text: String,
    isMine: Boolean = false
) {
    val shape = if (isMine) RightMessageBubbleShape() else LeftMessageBubbleShape()
    val padding = if (isMine) {
        PaddingValues(start = 16.dp, end = 27.dp, top = 16.dp, bottom = 16.dp)
    } else {
        PaddingValues(start = 27.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    }

    Box(
        modifier = Modifier
            .clip(shape)
            .background(YakssokTheme.color.grey50)
            .padding(padding)
    ) {
        Text(
            text = text,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey950
        )
    }
}