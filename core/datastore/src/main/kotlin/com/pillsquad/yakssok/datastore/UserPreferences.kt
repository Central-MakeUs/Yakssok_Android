package com.pillsquad.yakssok.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_PROFILE_IMG = stringPreferencesKey("user_profile_img")
        private val MEDICATION_COUNT = intPreferencesKey("medication_count")
        private val MATE_COUNT = intPreferencesKey("mate_count")
        private val FCM_TOKEN = stringPreferencesKey("fcm_token")
        private val PUSH_AGREEMENT = booleanPreferencesKey("push_agreement")
        private val TUTORIAL_COMPLETE = booleanPreferencesKey("tutorial_complete")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("user_access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("user_refresh_token")
        private val OAUTH_TYPE = stringPreferencesKey("oauth_type")
        private val INVITE_CODE = stringPreferencesKey("invite_code")
    }

    val userNameFlow: Flow<String> = dataStore.data
        .map { it[USER_NAME] ?: "" }

    val userProfileImgFlow: Flow<String> = dataStore.data
        .map { it[USER_PROFILE_IMG] ?: "" }

    val medicationCountFlow: Flow<Int> = dataStore.data
        .map { it[MEDICATION_COUNT] ?: 0 }

    val mateCountFlow: Flow<Int> = dataStore.data
        .map { it[MATE_COUNT] ?: 0 }

    val fcmTokenFlow: Flow<String> = dataStore.data
        .map { it[FCM_TOKEN] ?: "" }

    val pushAgreementFlow: Flow<Boolean> = dataStore.data
        .map { it[PUSH_AGREEMENT] ?: false }

    val tutorialCompleteFlow: Flow<Boolean> = dataStore.data
        .map { it[TUTORIAL_COMPLETE] ?: false }

    val accessTokenFlow: Flow<String> = dataStore.data
        .map { it[ACCESS_TOKEN_KEY] ?: "" }

    val refreshTokenFlow: Flow<String> = dataStore.data
        .map { it[REFRESH_TOKEN_KEY] ?: "" }

    val oauthTypeFlow: Flow<String> = dataStore.data
        .map { it[OAUTH_TYPE] ?: "" }

    val inviteCodeFlow: Flow<String> = dataStore.data
        .map { it[INVITE_CODE] ?: "" }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { it[USER_NAME] = userName }
    }

    suspend fun saveUserProfileImg(profileImg: String) {
        dataStore.edit { it[USER_PROFILE_IMG] = profileImg }
    }

    suspend fun saveMedicationCount(count: Int) {
        dataStore.edit { it[MEDICATION_COUNT] = count }
    }

    suspend fun saveMateCount(count: Int) {
        dataStore.edit { it[MATE_COUNT] = count }
    }

    suspend fun saveFcmToken(token: String) {
        dataStore.edit { it[FCM_TOKEN] = token }
    }

    suspend fun savePushAgreement(agreement: Boolean) {
        dataStore.edit { it[PUSH_AGREEMENT] = agreement }
    }

    suspend fun saveTutorialComplete(complete: Boolean) {
        dataStore.edit { it[TUTORIAL_COMPLETE] = complete }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { it[ACCESS_TOKEN_KEY] = token }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { it[REFRESH_TOKEN_KEY] = token }
    }

    suspend fun saveOauthType(oauthType: String) {
        dataStore.edit { it[OAUTH_TYPE] = oauthType }
    }

    suspend fun saveInviteCode(inviteCode: String) {
        dataStore.edit { it[INVITE_CODE] = inviteCode }
    }

    suspend fun clearTokens() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN_KEY)
            it.remove(REFRESH_TOKEN_KEY)
        }
    }

    suspend fun clearAllData() {
        dataStore.edit {
            it.clear()
        }
    }
}