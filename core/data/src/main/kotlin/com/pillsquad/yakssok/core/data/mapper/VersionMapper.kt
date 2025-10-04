package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.firebase.model.VersionModel
import com.pillsquad.yakssok.core.model.RemoteConfig

fun VersionModel.toRemoteConfig(): RemoteConfig =
    RemoteConfig(
        minSupportedVersion = this.minSupportedVersion,
        recommendedVersion = this.recommendedVersion
    )