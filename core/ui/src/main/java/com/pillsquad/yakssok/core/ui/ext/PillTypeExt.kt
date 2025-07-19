package com.pillsquad.yakssok.core.ui.ext

import androidx.compose.ui.graphics.Color
import com.pillsquad.yakssok.core.model.PillType

fun PillType.toBackground(): Color {
    return when (this) {
        PillType.MENTAL -> Color(0xFFE2DAFF)
        PillType.BEAUTY -> Color(0xFFE1FFE4)
        PillType.CHRONIC -> Color(0xFFE0F2FF)
        PillType.DIET -> Color(0xFFFEF0FF)
        PillType.TEMPORARY -> Color(0xFFFFF7E9)
        PillType.SUPPLEMENT -> Color(0xFFFFEBD9)
        PillType.OTHER -> Color(0xFFFFE3E3)
    }
}