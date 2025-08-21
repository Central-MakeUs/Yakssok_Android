package com.pillsquad.yakssok.widget.screen

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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(imageResource),
            contentDescription = "복약 처리",
            modifier = GlanceModifier
                .width(80.dp)
                .height(34.dp)
                .clickable(onClick = onAction)
        )

        Spacer(GlanceModifier.height(16.dp))

        LargeText(title, 20)
        SmallText(subTitle, 16)

        Spacer(GlanceModifier.height(10.dp))

        ExtraText(progress)
    }
}