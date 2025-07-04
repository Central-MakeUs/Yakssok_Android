package com.pillsquad.yakssok.feature.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun SplashScreen(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.primary400)
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.img_splash_logo),
            contentDescription = "yakssok logo",
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    SplashScreen()
}