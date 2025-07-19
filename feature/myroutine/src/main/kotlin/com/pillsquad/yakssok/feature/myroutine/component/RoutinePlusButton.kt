package com.pillsquad.yakssok.feature.myroutine.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun RoutinePlusButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = YakssokTheme.color.grey200,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .padding(start = 12.dp, end = 6.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "복약추가하기",
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Add,
            contentDescription = "add icon",
            tint = YakssokTheme.color.grey400
        )
    }
}