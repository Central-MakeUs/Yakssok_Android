package com.pillsquad.yakssok.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.R
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
fun YakssokTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    isLogo: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onNavigateAlarm: (() -> Unit)? = null,
    onNavigateSetting: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 16.dp)
    ) {
        onBackClick?.let {
            Box(
                modifier = Modifier.align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                YakssokIconButton(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = onBackClick,
                )
            }
        }

        if (isLogo) {
            Box(
                modifier = Modifier.align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.img_topbar_logo),
                    contentDescription = "yakssok logo",
                    tint = Color.Unspecified
                )
            }
        }

        title?.let {
            Box(
                modifier = Modifier.align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = YakssokTheme.typography.subtitle2,
                    color = YakssokTheme.color.grey900
                )
            }
        }

        if (onNavigateAlarm != null && onNavigateSetting != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                YakssokIconButton(
                    iconId = R.drawable.ic_alarm,
                    onClick = onNavigateAlarm,
                )
                Spacer(modifier = Modifier.size(16.dp))
                YakssokIconButton(
                    iconId = R.drawable.ic_dehaze,
                    onClick = onNavigateSetting,
                )
            }
        }
    }
}