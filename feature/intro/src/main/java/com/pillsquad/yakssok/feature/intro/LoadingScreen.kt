package com.pillsquad.yakssok.feature.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault

@Composable
internal fun LoadingScreen(
    backgroundColor: Color = YakssokTheme.color.primary400,
    iconId: Int = R.drawable.img_splash_logo
) {
    Box(
        modifier = Modifier.yakssokDefault(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = "yakssok logo",
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    LoadingScreen()
}