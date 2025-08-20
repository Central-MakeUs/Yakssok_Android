package com.pillsquad.yakssok.widget

import android.content.Context
import androidx.work.*

fun scheduleWidgetSync(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val periodic = PeriodicWorkRequestBuilder<SyncWidgetWorker>(15, java.util.concurrent.TimeUnit.MINUTES)
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