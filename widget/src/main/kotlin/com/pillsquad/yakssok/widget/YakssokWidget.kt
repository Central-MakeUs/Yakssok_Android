package com.pillsquad.yakssok.widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.pillsquad.yakssok.widget.screen.RectCard
import com.pillsquad.yakssok.widget.screen.SquareCard
import kotlinx.coroutines.runBlocking

class YakssokWidget : GlanceAppWidget() {
    override val sizeMode = SizeMode.Responsive(
        setOf(
            DpSize(150.dp, 50.dp),
            DpSize(225.dp, 100.dp)
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val snapShot = remember { runBlocking { "" } }
            val size = LocalSize.current
            val isTall = size.height >= 80.dp
            Log.e("Widget", "$size")

            if (isTall) SquareCard("지금 먹을 약", "pm 1:00 유산균", "1/3회", true) else RectCard("지금 먹을 약", "pm 1:00 유산균", true)
        }
    }
}