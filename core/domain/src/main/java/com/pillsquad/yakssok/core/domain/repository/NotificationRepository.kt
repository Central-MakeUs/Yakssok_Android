package com.pillsquad.yakssok.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun checkFcmToken()
}