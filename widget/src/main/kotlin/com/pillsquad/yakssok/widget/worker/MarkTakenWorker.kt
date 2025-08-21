package com.pillsquad.yakssok.widget.worker

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pillsquad.yakssok.core.domain.usecase.widget.MarkTakenAndRefreshUseCase
import com.pillsquad.yakssok.widget.YakssokWidget
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class MarkTakenWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun markTakenAndRefresh(): MarkTakenAndRefreshUseCase
    }

    override suspend fun doWork(): Result {
        val deps = EntryPointAccessors.fromApplication(applicationContext, Deps::class.java)
        val routineId = inputData.getInt("routineId", -1)
        val targetAppWidgetId = inputData.getInt("targetAppWidgetId", -1)
        if (routineId == -1) return Result.failure()

        val r = deps.markTakenAndRefresh().invoke(routineId)
        r.onSuccess {
            Log.e("MarkTakenWorker", "success $it")
        }.onFailure {
            Log.e("MarkTakenWorker", "failure $it")
        }
        return if (r.isSuccess) {
            val mgr = GlanceAppWidgetManager(applicationContext)
            val ids = mgr.getGlanceIds(YakssokWidget::class.java)
            val target = ids.firstOrNull { mgr.getAppWidgetId(it) == targetAppWidgetId }
            if (target != null) YakssokWidget().update(applicationContext, target)
            else YakssokWidget().updateAll(applicationContext)

            Result.success()
        } else {
            Result.retry()
        }
    }
}