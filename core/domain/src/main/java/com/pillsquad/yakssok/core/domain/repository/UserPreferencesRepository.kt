package com.pillsquad.yakssok.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveFcmToken(token: String)
    suspend fun getPushAgreement(): Boolean
    fun getTutorialComplete(): Flow<Boolean>
    suspend fun saveTutorialComplete(isComplete: Boolean = true)
}