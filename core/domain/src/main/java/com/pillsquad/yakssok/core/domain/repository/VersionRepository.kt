package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.RemoteConfig

interface VersionRepository {
    suspend fun fetchRemoteVersion(): Result<RemoteConfig>
}