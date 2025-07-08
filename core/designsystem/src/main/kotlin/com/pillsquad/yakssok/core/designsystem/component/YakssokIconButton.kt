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
    size: Int = 24,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(size.dp)
            .background(Color.Transparent),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(size.dp),
            painter = painterResource(iconId),
            contentDescription = "back",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun YakssokIconButton(
    imageVector: ImageVector,
    size: Int = 24,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(size.dp)
            .background(Color.Transparent),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(size.dp),
            imageVector = imageVector,
            contentDescription = "back",
            tint = Color.Unspecified
        )
    }
}