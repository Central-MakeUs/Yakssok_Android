package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun NumberIndicator(
    curPage: Int,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(3) { index ->
            val isSelected = index == curPage

            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(
                        color = if (isSelected) YakssokTheme.color.primary200 else YakssokTheme.color.grey200,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${index + 1}",
                    style = YakssokTheme.typography.body2,
                    color = if (isSelected) YakssokTheme.color.primary500 else YakssokTheme.color.grey400,
                )
            }
        }
    }
}
