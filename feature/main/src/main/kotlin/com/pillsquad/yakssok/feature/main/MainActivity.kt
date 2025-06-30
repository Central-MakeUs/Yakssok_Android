package com.pillsquad.yakssok.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()

            YakssokTheme {
                MainScreen(
                    navigator = navigator,
                    viewModel = viewModel
                )
            }
        }
    }
}