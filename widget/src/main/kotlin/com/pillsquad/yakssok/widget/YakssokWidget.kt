package com.pillsquad.yakssok.widget

import android.content.Context
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.pillsquad.yakssok.core.domain.usecase.widget.ObserveWidgetSnapshotUseCase
import com.pillsquad.yakssok.widget.screen.RectCard
import com.pillsquad.yakssok.widget.screen.SquareCard
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
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
            val deps = EntryPointAccessors.fromApplication(context, Deps::class.java)
            val snapShot = remember { runBlocking { deps.observeUC().invoke().first() } }

            val size = LocalSize.current
            val isTall = size.height >= 80.dp

            if (isTall) {
                SquareCard(
                    title = "지금 먹을 약",
                    subTitle = snapShot.subTitle,
                    progress = snapShot.progress,
                    isTaken = snapShot.nextRoutineId == null
                )
            } else {
                RectCard(
                    title = "지금 먹을 약",
                    subTitle = snapShot.subTitle,
                    isTaken = snapShot.nextRoutineId == null
                )
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun observeUC(): ObserveWidgetSnapshotUseCase
    }
}