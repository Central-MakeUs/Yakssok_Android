package com.pillsquad.yakssok.core.push

import android.app.PendingIntent
import android.content.Context

interface PushNavigation {
    fun appLaunchPendingIntent(context: Context): PendingIntent
}