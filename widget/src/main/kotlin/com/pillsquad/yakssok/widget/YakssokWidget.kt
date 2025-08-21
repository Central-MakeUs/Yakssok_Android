package com.pillsquad.yakssok.widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.domain.usecase.widget.ObserveWidgetSnapshotUseCase
import com.pillsquad.yakssok.core.model.WidgetItem
import com.pillsquad.yakssok.widget.screen.RectCard
import com.pillsquad.yakssok.widget.screen.SquareCard
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalTime

class YakssokWidget : GlanceAppWidget() {
    override val sizeMode = SizeMode.Responsive(
        setOf(
            DpSize(150.dp, 50.dp),
            DpSize(225.dp, 100.dp)
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val deps = EntryPointAccessors.fromApplication(context, Deps::class.java)
        val snapShot = deps.observeUseCase().invoke().first()

        provideContent {
            val now = LocalTime.now()
            val candidate = findCandidate(snapShot.rows, now)
            Log.e("YakssokWidget", "provideContent $candidate")

            val pillAction: Action = if (candidate != null) {
                actionRunCallback<MarkTakenAction>(
                    actionParametersOf(WidgetActionKeys.RoutineId to candidate.routineId)
                )
            } else {
                launchAppAction()
            }

            val sub = candidate?.let {
                val t = LocalTime.parse(it.intakeTime)
                val h = if (t.hour == 0 || t.hour == 12) 12 else t.hour % 12
                val ampm = if (t.hour < 12) "am" else "pm"
                val m = t.minute.toString().padStart(2, '0')
                "$ampm $h:$m ${it.medicationName}"
            } ?: "오늘은 없어요!"

            val size = LocalSize.current
            val isTall = size.height >= 80.dp

            if (isTall) {
                SquareCard(
                    title = "지금 먹을 약",
                    subTitle = sub,
                    progress = snapShot.progress,
                    isTaken = candidate?.isTaken ?: false,
                    onAction = pillAction
                )
            } else {
                RectCard(
                    title = "지금 먹을 약",
                    subTitle = sub,
                    isTaken = candidate?.isTaken ?: false,
                    onAction = pillAction
                )
            }
        }
    }

    private fun findCandidate(rows: List<WidgetItem>, now: LocalTime): WidgetItem? {
        val future = rows
            .filter { !it.isTaken }
            .map { it to LocalTime.parse(it.intakeTime) }
            .filter { (_, t) -> t > now }
            .minByOrNull { (_, t) -> t }
            ?.first

        if (future != null) return future

        return rows
            .filter { !it.isTaken }
            .map { it to LocalTime.parse(it.intakeTime) }
            .filter { (_, t) -> t <= now }
            .maxByOrNull { (_, t) -> t }
            ?.first
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun observeUseCase(): ObserveWidgetSnapshotUseCase
    }
}