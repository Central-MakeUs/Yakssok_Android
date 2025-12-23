package com.pillsquad.yakssok.widget

import android.content.Context
import android.util.Log
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
import com.pillsquad.yakssok.widget.ui.RectCard
import com.pillsquad.yakssok.widget.ui.SquareCard
import com.pillsquad.yakssok.widget.ui.WideCard
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalTime

class YakssokWidget : GlanceAppWidget() {
    override val sizeMode = SizeMode.Responsive(
        setOf(
            DpSize(150.dp, 50.dp),
            DpSize(225.dp, 100.dp),
            DpSize(375.dp, 150.dp)
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val deps = EntryPointAccessors.fromApplication(context, Deps::class.java)
        val snapShot = deps.observeUseCase().invoke().first()

        provideContent {
            val size = LocalSize.current
            val now = LocalTime.now()

            val candidate = findCandidate(snapShot.rows, now)

            val pillAction: Action = if (candidate != null) {
                actionRunCallback<MarkTakenAction>(
                    actionParametersOf(WidgetActionKeys.RoutineId to candidate.routineId)
                )
            } else {
                launchAppAction()
            }

            val sub = getSubTitle(candidate)

            when (size.width) {
                150.dp -> {
                    RectCard(
                        title = "지금 먹을 약",
                        subTitle = sub,
                    )
                }
                225.dp -> {
                    SquareCard(
                        title = "지금 먹을 약",
                        subTitle = sub,
                        progress = snapShot.progress,
                        isTaken = candidate?.isTaken ?: false,
                        onAction = pillAction
                    )
                }
                375.dp -> {
                    WideCard(
                        list = snapShot.rows,
                        onAction = pillAction
                    )
                }
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

    private fun getSubTitle(candidate: WidgetItem?): String = candidate?.let{
        val t = LocalTime.parse(it.intakeTime)
        val h = if (t.hour == 0 || t.hour == 12) 12 else t.hour % 12
        val amPm = if (t.hour < 12) "am" else "pm"
        val m = t.minute.toString().padStart(2, '0')
        "$amPm $h:$m ${it.medicationName}"
    } ?: "오늘은 없어요!"

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun observeUseCase(): ObserveWidgetSnapshotUseCase
    }
}