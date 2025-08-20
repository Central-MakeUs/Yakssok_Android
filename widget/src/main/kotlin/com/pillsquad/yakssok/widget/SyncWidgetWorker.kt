package com.pillsquad.yakssok.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pillsquad.yakssok.core.domain.usecase.widget.SyncWidgetSnapshotUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class SyncWidgetWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun syncUC(): SyncWidgetSnapshotUseCase
    }

    override suspend fun doWork(): Result {
        val deps = EntryPointAccessors.fromApplication(
            applicationContext,
            Deps::class.java
        )
        val r = deps.syncUC().invoke()

        return if (r.isSuccess) {
            YakssokWidget().updateAll(applicationContext)
            Result.success()
        } else {
            Result.retry()
        }
    }

}