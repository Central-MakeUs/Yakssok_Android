package com.pillsquad.yakssok.core.domain.repository

interface UserPreferencesRepository {
    suspend fun saveFcmToken(token: String)
    suspend fun getPushAgreement(): Boolean
}