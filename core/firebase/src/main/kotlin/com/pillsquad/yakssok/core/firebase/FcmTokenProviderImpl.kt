package com.pillsquad.yakssok.core.firebase

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenProviderImpl @Inject constructor() : FcmTokenProvider {
    override suspend fun fetchFcmTokenOrNull(): String? =
        runCatching { FirebaseMessaging.getInstance().token.await() }.getOrNull()
}