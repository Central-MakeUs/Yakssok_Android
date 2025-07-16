package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.routine.R

@Composable
internal fun RoutineCheckBox(
    title: String,
    isTrued: Boolean,
    onClick: () -> Unit
) {
    val icResource = if (isTrued) R.drawable.ic_check_on else R.drawable.ic_check_off

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey950
        )
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = icResource),
                contentDescription = "check box",
                tint = Color.Unspecified
            )
        }
    }
}