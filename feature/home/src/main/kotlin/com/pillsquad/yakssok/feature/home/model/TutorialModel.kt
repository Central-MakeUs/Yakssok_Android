package com.pillsquad.yakssok.feature.home.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Rect

internal const val HIGHLIGHT_DURATION = 700
internal const val FADE_DURATION = 250
internal const val OVERLAY_FADE_DURATION = 300
internal const val OVERLAY_ALPHA_FULL = 0.7f
internal const val OVERLAY_ALPHA_DIM = 0.3f

internal val RectVectorConverter = TwoWayConverter<Rect, AnimationVector4D>(
    convertToVector = { rect ->
        AnimationVector4D(rect.left, rect.top, rect.right, rect.bottom)
    },
    convertFromVector = { vector ->
        Rect(vector.v1, vector.v2, vector.v3, vector.v4)
    }
)

@Stable
internal class TutorialTransitionState(
    initialOverlayAlpha: Float,
    initialRect: Rect
) {
    val rectAnim = Animatable(initialRect, RectVectorConverter)
    val overlayAlphaAnim = Animatable(initialOverlayAlpha)
    val explainAlphaAnim = Animatable(0f)

    val overlayAlpha: Float
        get() = overlayAlphaAnim.value

    val explainAlpha: Float
        get() = explainAlphaAnim.value

    val currentRect: Rect
        get() = rectAnim.value
}
