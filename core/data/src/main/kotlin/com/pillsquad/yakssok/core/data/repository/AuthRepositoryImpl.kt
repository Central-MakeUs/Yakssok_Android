package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toResultForLogin
import com.pillsquad.yakssok.core.domain.repository.AuthRepository
import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.JoinRequest
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRetrofitDataSource: AuthDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : AuthRepository {
    override suspend fun joinUser(
        accessToken: String,
        nickName: String,
        pushAgreement: Boolean
    ): Result<Unit> {
        val params = JoinRequest(
            oauthAuthorizationCode = accessToken,
            oauthType = "kakao",
            nonce = "",
            nickName = nickName,
        )

        return authRetrofitDataSource.joinUser(params = params).toResult(transform = { it })
    }

    override suspend fun loginUser(accessToken: String): Result<Boolean> {
        val params = LoginRequest(
            oauthAuthorizationCode = accessToken,
            oauthType = "kakao"
        )

        val response = authRetrofitDataSource.loginUser(params = params)

        if (response is ApiResponse.Success) {
            userLocalDataSource.saveAccessToken(response.data.accessToken)
            userLocalDataSource.saveRefreshToken(response.data.refreshToken)
        }

        return response.toResultForLogin()
    }

    override fun checkToken(): Flow<Boolean> {
        return combine(
            userLocalDataSource.accessTokenFlow,
            userLocalDataSource.refreshTokenFlow
        ) { accessToken, refreshToken ->
            accessToken.isNotBlank() && refreshToken.isNotBlank()
        }
    }
}