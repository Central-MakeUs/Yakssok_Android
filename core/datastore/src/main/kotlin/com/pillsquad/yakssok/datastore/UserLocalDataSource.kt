package com.pillsquad.yakssok.datastore

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
    private val userPreferences: UserPreferences
) {
    val isInitializedFlow: Flow<Boolean> = userPreferences.isInitializedFlow

    val userNameFlow: Flow<String> = userPreferences.userNameFlow
    val userProfileImgFlow: Flow<String> = userPreferences.userProfileImgFlow
    val medicationCountFlow: Flow<Int> = userPreferences.medicationCountFlow
    val mateCountFlow: Flow<Int> = userPreferences.mateCountFlow

    val deviceIdFlow: Flow<String> = userPreferences.deviceIdFlow
    val fcmTokenFlow: Flow<String> = userPreferences.fcmTokenFlow
    val pushAgreementFlow: Flow<Boolean> = userPreferences.pushAgreementFlow

    val tutorialCompleteFlow: Flow<Boolean> = userPreferences.tutorialCompleteFlow

    val accessTokenFlow: Flow<String> = userPreferences.accessTokenFlow
    val refreshTokenFlow: Flow<String> = userPreferences.refreshTokenFlow

    val inviteCodeFlow: Flow<String> = userPreferences.inviteCodeFlow

    suspend fun saveInfo(
        nickName: String,
        profileImg: String,
        medicationCount: Int,
        mateCount: Int
    ) {
        userPreferences.saveInfo(nickName, profileImg, medicationCount, mateCount)
    }

    suspend fun saveSession(access: String, refresh: String, isInit: Boolean) {
        userPreferences.saveSession(access, refresh, isInit)
    }

    suspend fun saveInitialized(isInitialized: Boolean) {
        userPreferences.saveInitialized(isInitialized)
    }

    suspend fun saveUserName(userName: String) {
        userPreferences.saveUserName(userName)
    }

    suspend fun saveUserProfileImg(profileImg: String) {
        userPreferences.saveUserProfileImg(profileImg)
    }

    suspend fun saveMedicationCount(count: Int) {
        userPreferences.saveMedicationCount(count)
    }

    suspend fun saveMateCount(count: Int) {
        userPreferences.saveMateCount(count)
    }

    suspend fun saveDeviceId(deviceId: String) {
        userPreferences.saveDeviceId(deviceId)
    }

    suspend fun saveFcmToken(token: String) {
        userPreferences.saveFcmToken(token)
    }

    suspend fun savePushAgreement(agreement: Boolean) {
        userPreferences.savePushAgreement(agreement)
    }

    suspend fun saveTutorialComplete(complete: Boolean) {
        userPreferences.saveTutorialComplete(complete)
    }

    suspend fun saveAccessToken(token: String) {
        userPreferences.saveAccessToken(token)
    }

    suspend fun saveRefreshToken(token: String) {
        userPreferences.saveRefreshToken(token)
    }

    suspend fun saveInviteCode(inviteCode: String) {
        userPreferences.saveInviteCode(inviteCode)
    }

    suspend fun clearTokens() {
        userPreferences.clearTokens()
    }

    suspend fun clearAllData() {
        userPreferences.clearAllData()
    }
}