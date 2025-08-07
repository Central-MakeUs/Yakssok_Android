package com.pillsquad.yakssok.core.ui.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.yakssokDefault(color: Color): Modifier {
    return this
        .fillMaxSize()
        .background(color)
        .systemBarsPadding()
        .padding(horizontal = 16.dp)
        .padding(bottom = 16.dp)
}