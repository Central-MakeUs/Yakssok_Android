package com.pillsquad.yakssok.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import com.pillsquad.yakssok.widget.R
import com.pillsquad.yakssok.widget.launchAppAction

@Composable
internal fun SquareCard(
    title: String,
    subTitle: String,
    progress: String,
    isTaken: Boolean,
    onAction: Action
) {
    val imageResource = if (isTaken) {
        R.drawable.ic_widget_logo_true
    } else {
        R.drawable.ic_widget_logo_false
    }

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(Color(0xFFFFFFFF), Color(0xFFFFFFFF)))
            .clickable(launchAppAction())
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_widget_logo_true),
            contentDescription = "복약 처리",
            modifier = GlanceModifier
                .width(60.dp)
                .height(26.dp)
//                .clickable(onClick = onAction)
        )

        Spacer(GlanceModifier.height(12.dp))

        LargeText(title, 20)
        SmallText(
            text = subTitle,
            size = 16,
            maxLines = 2
        )

        Column(
            modifier = GlanceModifier.defaultWeight(),
            horizontalAlignment = Alignment.End,
            verticalAlignment = Alignment.Bottom
        ) {
            ExtraText(progress)
        }
    }
}