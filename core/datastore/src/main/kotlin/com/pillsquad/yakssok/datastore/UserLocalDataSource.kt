package com.pillsquad.yakssok.datastore

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
    private val userPreferences: UserPreferences
) {
    val userNameFlow: Flow<String> = userPreferences.userNameFlow
    val userProfileImgFlow: Flow<String> = userPreferences.userProfileImgFlow
    val medicationCountFlow: Flow<Int> = userPreferences.medicationCountFlow
    val mateCountFlow: Flow<Int> = userPreferences.mateCountFlow
    val fcmTokenFlow: Flow<String> = userPreferences.fcmTokenFlow
    val pushAgreementFlow: Flow<Boolean> = userPreferences.pushAgreementFlow
    val tutorialCompleteFlow: Flow<Boolean> = userPreferences.tutorialCompleteFlow
    val accessTokenFlow: Flow<String> = userPreferences.accessTokenFlow
    val refreshTokenFlow: Flow<String> = userPreferences.refreshTokenFlow
    val oauthTypeFlow: Flow<String> = userPreferences.oauthTypeFlow
    val inviteCodeFlow: Flow<String> = userPreferences.inviteCodeFlow


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

    suspend fun saveOauthType(oauthType: String) {
        userPreferences.saveOauthType(oauthType)
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