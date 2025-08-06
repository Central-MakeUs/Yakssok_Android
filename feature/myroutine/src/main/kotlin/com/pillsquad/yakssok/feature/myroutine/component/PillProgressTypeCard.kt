package com.pillsquad.yakssok.feature.myroutine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.feature.myroutine.toColor

@Composable
internal fun PillProgressTypeCard(
    progressType: MedicationStatus
) {
    val (bgColor, txtColor) = progressType.toColor()

    Row(
        modifier = Modifier
            .background(
                color = bgColor,
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = progressType.krName,
            style = YakssokTheme.typography.caption,
            color = txtColor
        )
    }
}