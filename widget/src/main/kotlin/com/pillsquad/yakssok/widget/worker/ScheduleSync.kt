package com.pillsquad.yakssok.widget.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.pillsquad.yakssok.widget.model.WorkNames
import java.util.concurrent.TimeUnit

fun scheduleWidgetSync(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val periodic = PeriodicWorkRequestBuilder<SyncWidgetWorker>(15, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "widget-sync-periodic",
        ExistingPeriodicWorkPolicy.UPDATE,
        periodic
    )

    val oneTime = OneTimeWorkRequestBuilder<SyncWidgetWorker>()
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "widget-sync-now",
        ExistingWorkPolicy.REPLACE,
        oneTime
    )
}