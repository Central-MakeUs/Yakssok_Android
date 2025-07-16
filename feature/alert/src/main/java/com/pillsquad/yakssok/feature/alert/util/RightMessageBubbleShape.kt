package com.pillsquad.yakssok.feature.alert.util

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class RightMessageBubbleShape(
    private val cornerRadius: Dp = 12.dp,
    private val tailWidth: Dp = 11.dp,
    private val tailHeight: Dp = 15.dp,
    private val tailOffsetY: Dp = 13.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        with(density) {
            val corner = cornerRadius.toPx()
            val tailW = tailWidth.toPx()
            val tailH = tailHeight.toPx()
            val tailOffset = tailOffsetY.toPx()

            val path = Path().apply {
                moveTo(corner, 0f)

                lineTo(size.width - tailW - corner, 0f)
                arcTo(
                    rect = Rect(
                        left = size.width - tailW - 2 * corner,
                        top = 0f,
                        right = size.width - tailW,
                        bottom = 2 * corner
                    ),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                lineTo(size.width - tailW, tailOffset)

                // 꼬리
                lineTo(size.width, tailOffset)
                lineTo(size.width - tailW, tailOffset + tailH)

                lineTo(size.width - tailW, size.height - corner)
                arcTo(
                    rect = Rect(
                        left = size.width - tailW - 2 * corner,
                        top = size.height - 2 * corner,
                        right = size.width - tailW,
                        bottom = size.height
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                lineTo(corner, size.height)
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = size.height - 2 * corner,
                        right = 2 * corner,
                        bottom = size.height
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                lineTo(0f, corner)
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = 2 * corner,
                        bottom = 2 * corner
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                close()
            }

            return Outline.Generic(path)
        }
    }
}