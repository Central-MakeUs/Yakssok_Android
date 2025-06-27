package com.pillsquad.yakssok.core.designsystem.theme

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun YakssokTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors: YakssokColor = YakssokLightColor
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, view)

        // 앱이 시스템 패딩을 직접 제어
        WindowCompat.setDecorFitsSystemWindows(window, false)

        insetsController.isAppearanceLightStatusBars = !darkTheme
        insetsController.isAppearanceLightNavigationBars = !darkTheme

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    CompositionLocalProvider(
        LocalColor provides colors,
        LocalTypography provides YakssokTypography,
        content = content
    )
}

object YakssokTheme {
    val color: YakssokColor
        @Composable
        get() = LocalColor.current

    val typography: YakssokTypography
        @Composable
        get() = LocalTypography.current
}