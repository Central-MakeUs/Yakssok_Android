package com.pillsquad.yakssok.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.color.ColorProvider
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.pillsquad.yakssok.core.designsystem.theme.WidgetColor

@Composable
internal fun LargeText(
    text: String,
    size: Int = 16
) {
    Text(
        text = text,
        style = TextStyle(
            color = ColorProvider(WidgetColor.grey800, WidgetColor.grey800),
            fontFamily = FontFamily.SansSerif,
            fontSize = size.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        ),
        maxLines = 1
    )
}

@Composable
internal fun SmallText(
    text: String,
    size: Int = 14,
    maxLines: Int = 1
) {
    Text(
        text = text,
        style = TextStyle(
            color = ColorProvider(WidgetColor.grey600, WidgetColor.grey600),
            fontFamily = FontFamily.SansSerif,
            fontSize = size.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
        ),
        maxLines = maxLines
    )
}

@Composable
internal fun ExtraText(
    text: String
) {
    Text(
        modifier = GlanceModifier.fillMaxWidth(),
        text = text,
        style = TextStyle(
            color = ColorProvider(WidgetColor.grey800, WidgetColor.grey800),
            fontFamily = FontFamily.SansSerif,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        ),
        maxLines = 1
    )
}