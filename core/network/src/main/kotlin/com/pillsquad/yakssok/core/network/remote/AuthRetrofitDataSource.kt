package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.core.network.model.request.LogoutRequest
import com.pillsquad.yakssok.core.network.model.request.RefreshRequest
import com.pillsquad.yakssok.core.network.model.response.AccessTokenResponse
import com.pillsquad.yakssok.core.network.model.response.TokenResponse
import com.pillsquad.yakssok.core.network.service.AuthApi
import com.pillsquad.yakssok.core.network.service.TokenApi
import javax.inject.Inject

class AuthRetrofitDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val tokenApi: TokenApi
) : AuthDataSource {

    override suspend fun loginUser(params: LoginRequest): ApiResponse<TokenResponse> =
        tokenApi.loginUser(params)

    override suspend fun logoutUser(params: LogoutRequest): ApiResponse<Unit> =
        authApi.logoutUser(params)

    override suspend fun refreshToken(params: RefreshRequest): ApiResponse<AccessTokenResponse> =
        tokenApi.refreshToken(params)
}