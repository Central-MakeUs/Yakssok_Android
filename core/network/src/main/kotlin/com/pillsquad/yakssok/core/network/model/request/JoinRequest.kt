package com.pillsquad.yakssok.core.network.model.request

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class JoinRequest(
    val oauthAuthorizationCode: String,
    val oauthType: String,
    val nonce: String,
    val nickName: String,
    val pushAgreement: Boolean,
    val fcmToken: String
)