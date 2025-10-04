package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toRemoteConfig
import com.pillsquad.yakssok.core.domain.repository.VersionRepository
import com.pillsquad.yakssok.core.firebase.RemoteConfigProvider
import com.pillsquad.yakssok.core.model.RemoteConfig
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val remoteConfigProvider: RemoteConfigProvider,
): VersionRepository {
    override suspend fun fetchRemoteVersion(): Result<RemoteConfig> {
        return remoteConfigProvider.getRemoteVersion().map { it.toRemoteConfig() }
    }
}