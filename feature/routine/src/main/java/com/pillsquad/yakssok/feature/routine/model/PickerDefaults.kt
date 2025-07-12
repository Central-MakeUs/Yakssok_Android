package com.pillsquad.yakssok.feature.routine.model

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

object PickerDefaults {
    @Composable
    fun pickerStyle(
        textStyle: TextStyle = YakssokTheme.typography.header2,
        textColor: Color = YakssokTheme.color.grey950,
        itemSpacing: Dp = 16.dp
    ): PickerStyle {
        return PickerStyle(
            textStyle = textStyle,
            textColor = textColor,
            itemSpacing = itemSpacing
        )
    }

    @Composable
    fun pickerSelector(
        enabled: Boolean = true,
        shape: RoundedCornerShape = RoundedCornerShape(16.dp),
        color: Color = YakssokTheme.color.primary300,
        border: BorderStroke? = null
    ): PickerSelector {
        return PickerSelector(
            enabled = enabled,
            shape = shape,
            color = color,
            border = border
        )
    }

    fun curveEffect(
        alphaEnabled: Boolean = true,
        minAlpha: Float = 0.2f,
        scaleYEnabled: Boolean = true,
        minScaleY: Float = 0.8f
    ): CurveEffect {
        return CurveEffect(
            alphaEnabled = alphaEnabled,
            minAlpha = minAlpha,
            scaleYEnabled = scaleYEnabled,
            minScaleY = minScaleY
        )
    }

    const val VISIBLE_ITEM_COUNT: Int = 5
}

@Immutable
data class PickerStyle(
    val textStyle: TextStyle,
    val textColor: Color,
    val itemSpacing: Dp
)

@Immutable
data class CurveEffect(
    val alphaEnabled: Boolean = true,
    val minAlpha: Float = 0.2f,
    val scaleYEnabled: Boolean = true,
    val minScaleY: Float = 0.8f
) {
    fun calculateAlpha(distanceFromCenter: Float, maxDistance: Float): Float {
        if (!alphaEnabled) return 1f
        val ratio = (distanceFromCenter / maxDistance).coerceIn(0f, 1f)
        return ((1f - ratio) * (1f - minAlpha) + minAlpha)
    }

    fun calculateScaleY(distanceFromCenter: Float, maxDistance: Float): Float {
        if (!scaleYEnabled) return 1f
        val ratio = (distanceFromCenter / maxDistance).coerceIn(0f, 1f)
        return ((1f - ratio) * (1f - minScaleY) + minScaleY)
    }
}

@Immutable
data class PickerSelector(
    val enabled: Boolean,
    val shape: RoundedCornerShape,
    val color: Color,
    val border: BorderStroke?
)