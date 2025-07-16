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

class LeftMessageBubbleShape(
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
                // 시작점: 왼쪽 위 + tail 너비 + 코너
                moveTo(tailW + corner, 0f)

                // 상단 라인 → 우상단 라운드
                lineTo(size.width - corner, 0f)
                arcTo(
                    rect = Rect(
                        left = size.width - 2 * corner,
                        top = 0f,
                        right = size.width,
                        bottom = 2 * corner
                    ),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // 오른쪽 라인 → 우하단 라운드
                lineTo(size.width, size.height - corner)
                arcTo(
                    rect = Rect(
                        left = size.width - 2 * corner,
                        top = size.height - 2 * corner,
                        right = size.width,
                        bottom = size.height
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // 하단 라인 → 좌하단 라운드
                lineTo(tailW + corner, size.height)
                arcTo(
                    rect = Rect(
                        left = tailW,
                        top = size.height - 2 * corner,
                        right = tailW + 2 * corner,
                        bottom = size.height
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // 왼쪽 라인 아래 → 꼬리 전까지
                lineTo(tailW, tailOffset + tailH)

                // 꼬리
                lineTo(0f, tailOffset)
                lineTo(tailW, tailOffset)

                // 다시 위로
                lineTo(tailW, corner)
                arcTo(
                    rect = Rect(
                        left = tailW,
                        top = 0f,
                        right = tailW + 2 * corner,
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