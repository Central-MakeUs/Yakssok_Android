package com.pillsquad.yakssok.feature.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun IntroRoute(
    viewModel: IntroViewModel = hiltViewModel()
) {
    val loadingState by viewModel.isLoading.collectAsStateWithLifecycle()

    if (loadingState) {
        SplashScreen()
    } else {
        IntroScreen()
    }
}

@Composable
private fun IntroScreen(
) {
    Column(
        modifier = Modifier
            .background(YakssokTheme.color.grey50)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(bottom = 48.dp),
    ) {

    }
}

@Composable
private fun SplashScreen(
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