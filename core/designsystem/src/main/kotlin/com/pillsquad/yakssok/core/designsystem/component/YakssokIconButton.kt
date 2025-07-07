package com.pillsquad.yakssok.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun YakssokIconButton(
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(24.dp)
            .background(Color.Transparent),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(iconId),
            contentDescription = "back"
        )
    }
}

@Composable
fun YakssokIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(24.dp)
            .background(Color.Transparent),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = imageVector,
            contentDescription = "back"
        )
    }
}