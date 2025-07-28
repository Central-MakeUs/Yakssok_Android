package com.pillsquad.yakssok.core.ui.ext

import androidx.compose.ui.graphics.Color
import com.pillsquad.yakssok.core.model.MedicationType

fun MedicationType.toBackground(): Color {
    return when (this) {
        MedicationType.MENTAL -> Color(0xFFE2DAFF)
        MedicationType.BEAUTY -> Color(0xFFE1FFE4)
        MedicationType.CHRONIC -> Color(0xFFE0F2FF)
        MedicationType.DIET -> Color(0xFFFEF0FF)
        MedicationType.TEMPORARY -> Color(0xFFFFF7E9)
        MedicationType.SUPPLEMENT -> Color(0xFFFFEBD9)
        MedicationType.OTHER -> Color(0xFFFFE3E3)
    }
}