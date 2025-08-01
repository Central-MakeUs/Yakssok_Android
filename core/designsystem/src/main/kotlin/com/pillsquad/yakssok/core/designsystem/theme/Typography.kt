package com.pillsquad.yakssok.core.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.pillsquad.yakssok.core.designsystem.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_std_variable, FontWeight.Normal),
)

@Stable
object YakssokTypography {
    val lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )

    val header1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.4.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val header2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 31.2.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val subtitle1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val subtitle2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val body0 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val body1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val body2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )

    val caption = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        lineHeightStyle = lineHeightStyle,
        letterSpacing = (-0.025).em
    )
}