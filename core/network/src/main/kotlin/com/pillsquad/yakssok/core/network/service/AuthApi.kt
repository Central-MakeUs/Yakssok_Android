package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.LogoutRequest
import retrofit2.http.Body
import retrofit2.http.PUT

interface AuthApi {

    @PUT("/api/auth/logout")
    suspend fun logoutUser(
        @Body params: LogoutRequest
    ): ApiResponse<Unit>
}