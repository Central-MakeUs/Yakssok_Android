package com.pillsquad.yakssok.feature.mypage.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun InfoRow(
    @DrawableRes iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = YakssokTheme.color.grey150,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = iconRes),
                contentDescription = "profile image",
                tint = YakssokTheme.color.grey400
            )
            Text(
                text = title,
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey900
            )
        }

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "arrow right",
            tint = YakssokTheme.color.grey400
        )
    }
}

@Composable
internal fun InfoRow(
    title: String,
    isGranted: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = YakssokTheme.color.grey150,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey900
            )

            Switch(
                modifier = Modifier
                    .width(45.dp)
                    .height(24.dp),
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = isGranted,
                thumbContent = {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                color = if (checked) YakssokTheme.color.primary400 else YakssokTheme.color.grey300,
                                shape = CircleShape
                            )
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Transparent,
                    checkedTrackColor = YakssokTheme.color.grey50,
                    checkedBorderColor = YakssokTheme.color.grey200,
                    uncheckedThumbColor = Color.Transparent,
                    uncheckedTrackColor = YakssokTheme.color.grey50,
                    uncheckedBorderColor = YakssokTheme.color.grey200
                )
            )
        }

        if (!isGranted) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = YakssokTheme.color.grey500.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        }
    }
}