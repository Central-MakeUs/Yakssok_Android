package com.pillsquad.yakssok.core.designsystem.util

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ShadowDirection { ALL, TOP, BOTTOM, START, END }

fun Modifier.shadow(
    color: Color = Color.Black,
    blur: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    shape: Shape = RectangleShape,
    spread: Dp = 0.dp,
    direction: ShadowDirection = ShadowDirection.ALL
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val outline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply { this.color = color }
    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    val basePath = Path().apply {
        when (outline) {
            is Outline.Generic -> addPath(outline.path)
            is Outline.Rectangle -> addRect(outline.rect)
            is Outline.Rounded -> addRoundRect(outline.roundRect)
        }
    }

    val clipPath = when (direction) {
        ShadowDirection.BOTTOM -> Path().apply {
            addRect(Rect(0f, 0f, size.width, size.height - blur.toPx()))
        }
        ShadowDirection.TOP -> Path().apply {
            addRect(Rect(0f, blur.toPx(), size.width, size.height))
        }
        else -> null
    }

    val finalPath = if (clipPath != null) {
        Path().apply { op(basePath, clipPath, PathOperation.Intersect) }
    } else basePath

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        if (direction == ShadowDirection.ALL) {
            canvas.drawOutline(outline, paint)
        } else {
            canvas.drawPath(finalPath, paint)
        }
        canvas.restore()
    }
}