package com.pillsquad.yakssok.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalColor = staticCompositionLocalOf<YakssokColor> {
    error("LocalColor가 제공되지 않았습니다. Yakssok Theme을 적용했는지 확인해주세요.")
}