package com.pillsquad.yakssok.core.push

import android.app.PendingIntent
import android.content.Context

interface PushNavigation {
    // 일반 진입
    fun defaultPendingIntent(context: Context): PendingIntent
    // 필요한 화면 진입(현재는 복약으로 이름 지음)
    fun medicationPendingIntent(context: Context, data: Map<String, String>): PendingIntent
}