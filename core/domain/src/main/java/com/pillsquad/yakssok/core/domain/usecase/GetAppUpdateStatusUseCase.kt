package com.pillsquad.yakssok.core.domain.usecase

import android.accounts.NetworkErrorException
import com.pillsquad.yakssok.core.common.AppInfo
import com.pillsquad.yakssok.core.domain.repository.VersionRepository
import com.pillsquad.yakssok.core.model.UpdateType
import java.net.SocketTimeoutException
import javax.inject.Inject

class GetAppUpdateStatusUseCase @Inject constructor(
    private val repository: VersionRepository
) {
    suspend operator fun invoke(currentVersionCode: Int = AppInfo.VERSION_CODE): UpdateType {
        return try {
            val version = repository.fetchRemoteVersion().getOrThrow()

            when {
                currentVersionCode < version.minSupportedVersion -> UpdateType.FORCE
                currentVersionCode < version.recommendedVersion -> UpdateType.SOFT
                else -> UpdateType.NONE
            }
        } catch (e: Exception) {
            when (e) {
                is NetworkErrorException -> UpdateType.NETWORK
                is SocketTimeoutException -> UpdateType.NETWORK
                else -> UpdateType.ERROR
            }
        }
    }
}