package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.BuildConfig
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.AuthRepository
import com.pillsquad.yakssok.core.model.OAuthType
import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.core.network.model.request.LogoutRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthDataSource,
    private val local: UserLocalDataSource,
) : AuthRepository {

    override suspend fun loginUser(accessToken: String): Result<Boolean> {
        val params = LoginRequest(
            oauthAuthorizationCode = accessToken,
            oauthType = OAuthType.KAKAO.value,
        )

        return remote.loginUser(params = params)
            .toResult()
            .mapCatching { dto ->
                local.saveSession(dto.accessToken, dto.refreshToken, dto.isInitialized)
                dto.isInitialized
            }
    }

    override suspend fun putLogout(): Result<Unit> {
        val deviceId = local.deviceIdFlow.firstOrNull()
            ?.takeIf { it.isNotBlank() }
            ?: return Result.failure(IllegalStateException("deviceId is null or blank"))

        return remote.logoutUser(LogoutRequest(deviceId = deviceId))
            .toResult()
            .onSuccess { local.clearAllData() }
    }

    override suspend fun testLoginUser() {
        local.saveAccessToken(BuildConfig.MASTER_ACCESS)
        local.saveRefreshToken(BuildConfig.MASTER_REFRESH)
        local.saveInitialized(true)
    }

    override fun checkToken(): Flow<Boolean> {
        return combine(
            local.accessTokenFlow,
            local.refreshTokenFlow,
            local.isInitializedFlow
        ) { access, refresh, init ->
            access.isNotBlank() && refresh.isNotBlank() && init
        }.distinctUntilChanged()
    }
}