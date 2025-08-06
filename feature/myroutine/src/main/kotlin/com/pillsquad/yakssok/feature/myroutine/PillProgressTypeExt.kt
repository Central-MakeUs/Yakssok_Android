package com.pillsquad.yakssok.feature.myroutine

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MedicationStatus

// Background to TextColor
@Composable
fun MedicationStatus.toColor(): Pair<Color, Color> {
    return when (this) {
        MedicationStatus.PLANNED -> YakssokTheme.color.grey100 to YakssokTheme.color.grey900
        MedicationStatus.TAKING -> YakssokTheme.color.primary100 to YakssokTheme.color.primary400
        MedicationStatus.ENDED -> YakssokTheme.color.grey200 to YakssokTheme.color.grey500
    }
}