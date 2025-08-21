package com.pillsquad.yakssok.widget.worker

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pillsquad.yakssok.core.domain.usecase.widget.SyncWidgetSnapshotUseCase
import com.pillsquad.yakssok.widget.YakssokWidget
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class SyncWidgetWorker(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun syncUC(): SyncWidgetSnapshotUseCase
    }

    override suspend fun doWork(): Result {
        return try {
            val deps = EntryPointAccessors.fromApplication(
                appContext,
                Deps::class.java
            )
            val r = deps.syncUC().invoke()
            r.onSuccess {
                Log.e("YakssokWidget", "success $r")
            }.onFailure {
                Log.e("YakssokWidget", it.toString())
            }

            if (r.isSuccess) Result.success() else Result.retry()
        } catch (t: Throwable) {
            Log.e("YakssokWidget", t.toString(), t)
            Result.retry()
        } finally {
            YakssokWidget().updateAll(appContext)
        }
    }
}