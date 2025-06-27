package com.pillsquad.yakssok.core.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

interface YakssokColor {
    val grey50: Color
    val grey100: Color
    val grey200: Color
    val grey300: Color
    val grey400: Color
    val grey500: Color
    val grey600: Color
    val grey700: Color
    val grey800: Color
    val grey900: Color
    val grey950: Color
    val primary50: Color
    val primary100: Color
    val primary200: Color
    val primary300: Color
    val primary400: Color
    val primary500: Color
    val primary600: Color
    val primary700: Color
    val primary800: Color
    val primary900: Color
    val primary950: Color
    val subPurple: Color
    val subYellow: Color
    val subGreen: Color
    val subBlue: Color
}

@Stable
object YakssokLightColor : YakssokColor {
    override val grey50 = Color(0xFFF8F8F8)
    override val grey100 = Color(0xFFEFEFEF)
    override val grey200 = Color(0xFFDCDCDC)
    override val grey300 = Color(0xFFBDBDBD)
    override val grey400 = Color(0xFF989898)
    override val grey500 = Color(0xFF7C7C7C)
    override val grey600 = Color(0xFF656565)
    override val grey700 = Color(0xFF525252)
    override val grey800 = Color(0xFF464646)
    override val grey900 = Color(0xFF3D3D3D)
    override val grey950 = Color(0xFF202020)
    override val primary50 = Color(0xFFFFF2ED)
    override val primary100 = Color(0xFFFFE3D6)
    override val primary200 = Color(0xFFFDC2AB)
    override val primary300 = Color(0xFFFB9876)
    override val primary400 = Color(0xFFF9623E)
    override val primary500 = Color(0xFFF63A18)
    override val primary600 = Color(0xFF37220F)
    override val primary700 = Color(0xFFC0140E)
    override val primary800 = Color(0xFF981415)
    override val primary900 = Color(0xFF7B1313)
    override val primary950 = Color(0xFF42080B)
    override val subPurple = Color(0xFF7C24DB)
    override val subYellow = Color(0xFFE1CF33)
    override val subGreen = Color(0xFF3ADE4D)
    override val subBlue = Color(0xFF3C99D7)
}