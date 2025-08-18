package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.core.network.model.request.LogoutRequest
import com.pillsquad.yakssok.core.network.model.request.RefreshRequest
import com.pillsquad.yakssok.core.network.model.response.AccessTokenResponse
import com.pillsquad.yakssok.core.network.model.response.TokenResponse

interface AuthDataSource {
    suspend fun loginUser(params: LoginRequest): ApiResponse<TokenResponse>
    suspend fun logoutUser(params: LogoutRequest): ApiResponse<Unit>
    suspend fun refreshToken(params: RefreshRequest): ApiResponse<AccessTokenResponse>
}