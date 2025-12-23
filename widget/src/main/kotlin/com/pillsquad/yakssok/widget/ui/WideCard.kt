package com.pillsquad.yakssok.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.pillsquad.yakssok.core.common.formatLocalTime
import com.pillsquad.yakssok.core.designsystem.theme.WidgetColor
import com.pillsquad.yakssok.core.model.WidgetItem
import com.pillsquad.yakssok.widget.R
import com.pillsquad.yakssok.widget.launchAppAction
import kotlinx.datetime.LocalTime

@Composable
internal fun WideCard(
    list: List<WidgetItem>,
    onAction: Action
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(WidgetColor.white, WidgetColor.white))
            .clickable(launchAppAction())
            .padding(top = 28.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = GlanceModifier
                    .width(40.dp)
                    .height(17.dp),
                provider = ImageProvider(R.drawable.ic_widget_logo_true),
                contentDescription = "복약 처리",
            )
            Spacer(modifier = GlanceModifier.width(12.dp))
            LargeText(text = "오늘 먹어야 할 약", size = 20)
        }

        Spacer(modifier = GlanceModifier.height(16.dp))

        LazyColumn(
            modifier = GlanceModifier.defaultWeight(),
            horizontalAlignment = Alignment.Start,
        ) {
            items(count = list.size) { idx ->
                val item = list[idx]
                MedicineItem(item)
            }
        }
    }
}

@Composable
private fun MedicineItem(
    item: WidgetItem
) {
    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(ColorProvider(WidgetColor.grey100, WidgetColor.grey100))
            .cornerRadius(16.dp)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = GlanceModifier
                .defaultWeight()
                .padding(start = 16.dp, end = 12.dp, top = 14.dp, bottom = 14.dp),
            text = item.medicationName,
            style = TextStyle(
                color = ColorProvider(WidgetColor.grey800, WidgetColor.grey800),
                fontFamily = FontFamily.SansSerif,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            ),
            maxLines = 1
        )

        SmallText(formatLocalTime(LocalTime.parse(item.intakeTime)))
    }
}