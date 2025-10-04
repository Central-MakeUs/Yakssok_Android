package com.pillsquad.yakssok.core.firebase

import com.pillsquad.yakssok.core.firebase.model.VersionModel

interface RemoteConfigProvider {
    suspend fun getMinSupportedVersion(): Result<VersionModel>
}