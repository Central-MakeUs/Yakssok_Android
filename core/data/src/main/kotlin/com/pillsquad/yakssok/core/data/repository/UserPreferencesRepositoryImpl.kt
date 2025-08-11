package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.domain.repository.UserPreferencesRepository
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
) : UserPreferencesRepository {
    override suspend fun saveFcmToken(token: String) {
        userLocalDataSource.saveFcmToken(token)
    }

    override suspend fun getPushAgreement(): Boolean =
        userLocalDataSource.pushAgreementFlow.firstOrNull() ?: false
}