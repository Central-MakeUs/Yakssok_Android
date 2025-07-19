package com.pillsquad.yakssok.feature.myroutine

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.PillProgressType

// Background to TextColor
@Composable
fun PillProgressType.toColor(): Pair<Color, Color> {
    return when (this) {
        PillProgressType.BEFORE -> YakssokTheme.color.grey100 to YakssokTheme.color.grey900
        PillProgressType.IN_PROGRESS -> YakssokTheme.color.primary100 to YakssokTheme.color.primary400
        PillProgressType.AFTER -> YakssokTheme.color.grey200 to YakssokTheme.color.grey500
    }
}