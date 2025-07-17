package com.pillsquad.yakssok.feature.mate.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun DashedBorderBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    strokeWidth: Dp = 1.dp,
    dashLength: Float = 21f,
    gapLength: Float = 24f,
    borderColor: Color = YakssokTheme.color.grey400,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .drawBehind {
                val stroke = Stroke(
                    width = strokeWidth.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
                )
                drawRoundRect(
                    color = borderColor,
                    size = size,
                    style = stroke,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
            .padding(vertical = (14.5).dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}