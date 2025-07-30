package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import retrofit2.http.PUT

interface AuthApi {

    @PUT("/api/auth/logout")
    suspend fun logoutUser(
    ): ApiResponse<Unit>
}