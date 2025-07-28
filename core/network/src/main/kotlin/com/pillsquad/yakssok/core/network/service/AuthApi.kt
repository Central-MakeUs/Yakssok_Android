package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.JoinRequest
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.core.network.model.request.RefreshRequest
import com.pillsquad.yakssok.core.network.model.response.AccessTokenResponse
import com.pillsquad.yakssok.core.network.model.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {

    @POST("/api/auth/join")
    suspend fun joinUser(
        @Body params: JoinRequest
    ): ApiResponse<Unit>

    @POST("/api/auth/login")
    suspend fun loginUser(
        @Body params: LoginRequest
    ): ApiResponse<TokenResponse>

    @PUT("/api/auth/logout")
    suspend fun logoutUser(
    ): ApiResponse<Unit>

    @POST("/api/auth/reissue")
    suspend fun refreshToken(
        @Body params: RefreshRequest
    ): ApiResponse<AccessTokenResponse>
}