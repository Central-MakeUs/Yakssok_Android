package com.pillsquad.yakssok.core.push

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.push.model.YakssokChannel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationFactory @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun build(title:String, body:String, type: AlarmType, intent: PendingIntent?): Notification {
        val channel = when (type) {
            AlarmType.FEEL_GOOD  -> YakssokChannel.FEEL_GOOD
            AlarmType.PILL_SHAKE -> YakssokChannel.PILL_SHAKE
            AlarmType.SCOLD      -> YakssokChannel.SCOLD
            AlarmType.CALL       -> YakssokChannel.CALL
            AlarmType.VIBRATION  -> YakssokChannel.VIBRATION
        }
        return NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.img_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(intent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    fun notifyNow(n: Notification) {
        NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), n)
    }
}