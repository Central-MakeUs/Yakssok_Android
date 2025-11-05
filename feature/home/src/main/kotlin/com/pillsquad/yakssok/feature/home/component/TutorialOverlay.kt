package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.customInsets

@Composable
fun TutorialOverlay(
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
    highlightRect: Rect?,
    onNext: () -> Unit
) {
    val overlayColor = YakssokTheme.color.black.copy(alpha = 0.7f)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .customInsets(top = true, bottom = true)
        ) {
            topBar()
            content()
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onNext
                )
        ) {
            drawRect(color = overlayColor)

            highlightRect?.let {
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = Offset(it.left, it.top),
                    size = it.size,
                    cornerRadius = CornerRadius(16f, 16f),
                    blendMode = BlendMode.Clear
                )
            }
        }
    }
}