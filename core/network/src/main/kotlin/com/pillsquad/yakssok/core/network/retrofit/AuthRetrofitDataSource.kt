package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.core.network.api.AuthApi
import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.JoinRequest
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.core.network.model.request.RefreshRequest
import com.pillsquad.yakssok.core.network.model.response.AccessTokenResponse
import com.pillsquad.yakssok.core.network.model.response.TokenResponse
import javax.inject.Inject

class AuthRetrofitDataSource @Inject constructor(
    private val authApi: AuthApi
) : AuthDataSource {
    override suspend fun joinUser(params: JoinRequest): ApiResponse<Unit> =
        authApi.joinUser(params)

    override suspend fun loginUser(params: LoginRequest): ApiResponse<TokenResponse> =
        authApi.loginUser(params)

    override suspend fun logoutUser(): ApiResponse<Unit> =
        authApi.logoutUser()

    override suspend fun refreshToken(params: RefreshRequest): ApiResponse<AccessTokenResponse> =
        authApi.refreshToken(params)
}