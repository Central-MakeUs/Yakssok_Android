package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun <T> CircleRow(
    items: List<T>,
    selectedItems: List<T>,
    onClick: (T) -> Unit,
    labelMapper: (T) -> String,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        items.forEach { item ->
            val isSelected = item in selectedItems

            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelected) YakssokTheme.color.primary100 else YakssokTheme.color.grey100,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) YakssokTheme.color.primary400 else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onClick(item) }
                    )
                    .padding(horizontal = 17.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = labelMapper(item),
                    style = YakssokTheme.typography.body1,
                    color = if (isSelected) YakssokTheme.color.primary400 else YakssokTheme.color.grey400
                )
            }
        }
    }
}