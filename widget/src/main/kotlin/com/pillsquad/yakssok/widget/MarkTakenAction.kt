package com.pillsquad.yakssok.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.updateAll
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.pillsquad.yakssok.core.domain.usecase.widget.MarkTakenAndRefreshUseCase
import com.pillsquad.yakssok.widget.worker.MarkTakenWorker
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

object WidgetActionKeys {
    val RoutineId = ActionParameters.Key<Int>("routine_id")
}

class MarkTakenAction : ActionCallback {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps { fun markUseCase(): MarkTakenAndRefreshUseCase }

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        // Todo: Local만 변경하고 update 호출해보기

        val idInt = GlanceAppWidgetManager(context).getAppWidgetId(glanceId)
        Log.d("MarkTakenAction", "onAction glanceId=$idInt")

        val routineId = parameters[WidgetActionKeys.RoutineId] ?: return
        val deps = EntryPointAccessors.fromApplication(context, Deps::class.java)

        deps.markUseCase().invoke(routineId)
            .onSuccess {
                YakssokWidget().updateAll(context)
            }.onFailure {
                Log.e("MarkTakenAction", "failed to mark taken", it)
            }
    }
}

private fun enqueueMarkWorker(context: Context, scheduleId: Int) {
    val work = OneTimeWorkRequestBuilder<MarkTakenWorker>()
        .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
        .setInputData(workDataOf("schedule_id" to scheduleId))
        .build()
    WorkManager.getInstance(context).enqueueUniqueWork(
        "widget-mark-$scheduleId",
        ExistingWorkPolicy.REPLACE,
        work
    )
}