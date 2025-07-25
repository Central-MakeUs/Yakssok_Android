package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.data.mapper.toResultForLogin
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authRetrofitDataSource: AuthDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
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