package com.pillsquad.yakssok.core.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.pillsquad.yakssok.core.firebase.model.VersionModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigProviderImpl @Inject constructor (
    private val remoteConfig: FirebaseRemoteConfig
) : RemoteConfigProvider {

    override suspend fun getMinSupportedVersion(): Result<VersionModel> = runCatching {
        remoteConfig.fetchAndActivate().await()

        VersionModel(
            minSupportedVersion = remoteConfig.getString(MIN_SUPPORTED_VERSION_KEY),
            recommendedVersion = remoteConfig.getString(RECOMMENDED_VERSION_KEY)
        )
    }

    companion object {
        private const val MIN_SUPPORTED_VERSION_KEY = "minSupportedVersion"
        private const val RECOMMENDED_VERSION_KEY = "recommendedVersion"
    }
}
