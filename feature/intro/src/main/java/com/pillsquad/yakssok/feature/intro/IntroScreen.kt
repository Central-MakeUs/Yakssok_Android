package com.pillsquad.yakssok.feature.intro

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun IntroRoute(
    padding: PaddingValues = PaddingValues(),
    viewModel: IntroViewModel = hiltViewModel()
) {
    val modifier = Modifier.padding(padding)

}

@Composable
private fun IntroScreen(
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
) {

}