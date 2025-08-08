package com.pillsquad.yakssok.core.network.model.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class DeviceRequest(
    val deviceId: String,
    val fcmToken: String,
    val alertOn: Boolean
)
