package com.pillsquad.yakssok.core.ui.ext

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Dp.toPx(density: Density): Float {
    return with(density) { this@toPx.toPx() }
}

fun LayoutCoordinates.toRect(
    density: Density,
    verticalPadding: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp
): Rect {
    val verticalPaddingPx = with(density) { verticalPadding.toPx() }
    val horizontalPaddingPx = with(density) { horizontalPadding.toPx() }
    val pos = positionInRoot()

    return Rect(
        left = pos.x - horizontalPaddingPx,
        top = pos.y - verticalPaddingPx,
        right = pos.x + size.width + horizontalPaddingPx,
        bottom = pos.y + size.height + verticalPaddingPx
    )
}