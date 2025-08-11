package com.pillsquad.yakssok.core.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.push.mapper.toSoundType
import com.pillsquad.yakssok.core.push.model.YakssokChannel
import com.pillsquad.yakssok.core.sound.NotificationSoundResolver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelRegistry @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resolver: NotificationSoundResolver
) {
    fun ensureChannels() {
        createIfNeeded(YakssokChannel.FEEL_GOOD, "기분좋음", AlarmType.FEEL_GOOD)
        createIfNeeded(YakssokChannel.PILL_SHAKE, "약통흔들", AlarmType.PILL_SHAKE)
        createIfNeeded(YakssokChannel.SCOLD, "잔소리", AlarmType.SCOLD)
        createIfNeeded(YakssokChannel.CALL, "전화벨", AlarmType.CALL)
        createIfNeeded(YakssokChannel.VIBRATION, "진동", AlarmType.VIBRATION)
        createDefaultIfNeeded()
    }

    private fun nm() = context.getSystemService(NotificationManager::class.java)

    private fun createIfNeeded(id: String, name: String, type: AlarmType) {
        val manager = nm()
        if (manager.getNotificationChannel(id) != null) return
        val ch = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH).apply {
            resolver.uriFor(type.toSoundType())?.let { uri ->
                setSound(
                    uri, AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
                )
            }
            enableVibration(true)
        }
        manager.createNotificationChannel(ch)
    }

    private fun createDefaultIfNeeded() {
        val manager = nm()
        if (manager.getNotificationChannel(YakssokChannel.DEFAULT) != null) return
        manager.createNotificationChannel(
            NotificationChannel(
                YakssokChannel.DEFAULT,
                "기본 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )
    }
}