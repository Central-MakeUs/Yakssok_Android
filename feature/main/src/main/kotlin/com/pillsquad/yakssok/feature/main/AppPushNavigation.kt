package com.pillsquad.yakssok.feature.main

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pillsquad.yakssok.core.push.PushNavigation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPushNavigation @Inject constructor() : PushNavigation {
    override fun appLaunchPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            // 필요시 flags 조정:
            // - CLEAR_TOP: 이미 떠 있으면 해당 액티비티 위 스택 제거
            // - NEW_TASK: 백그라운드에서 새 태스크로 열기
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return PendingIntent.getActivity(
            context,
            12001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}