package com.pillsquad.yakssok.core.push

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pillsquad.yakssok.core.domain.usecase.SaveFcmTokenUseCase
import com.pillsquad.yakssok.core.domain.usecase.SyncUserDevicesUseCase
import com.pillsquad.yakssok.core.model.AlarmType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class YakssokFirebaseMessagingService : FirebaseMessagingService() {

    @Inject lateinit var channelRegistry: ChannelRegistry
    @Inject lateinit var notificationFactory: NotificationFactory
    @Inject lateinit var pushNavigation: PushNavigation
    @Inject lateinit var saveFcmToken: SaveFcmTokenUseCase
    @Inject lateinit var syncUserDevices: SyncUserDevicesUseCase

    override fun onNewToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                saveFcmToken(token)
                syncUserDevices()
            }
        }
    }

    override fun onMessageReceived(m: RemoteMessage) {
        channelRegistry.ensureChannels()

        m.notification?.let { n ->
            val pi = pushNavigation.defaultPendingIntent(this)
            notificationFactory.notifyNow(
                notificationFactory.build(n.title.orEmpty(), n.body.orEmpty(), AlarmType.FEEL_GOOD, pi)
            )
            return
        }

        if (m.data.isNotEmpty()) {
            val title = m.data["title"].orEmpty()
            val body  = m.data["body"].orEmpty()
            val type  = when (m.data["soundType"]?.uppercase()) {
                "FEEL_GOOD" -> AlarmType.FEEL_GOOD
                "PILL_SHAKE" -> AlarmType.PILL_SHAKE
                "SCOLD" -> AlarmType.SCOLD
                "CALL" -> AlarmType.CALL
                "VIBRATION" -> AlarmType.VIBRATION
                else -> AlarmType.FEEL_GOOD
            }
            val pi = pushNavigation.medicationPendingIntent(this, m.data)
            notificationFactory.notifyNow(notificationFactory.build(title, body, type, pi))
        }
    }
}