package com.pillsquad.yakssok.core.network.api

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import retrofit2.http.GET

interface UserApi {
    @GET("/api/users/me")
    suspend fun getMyInfo(): ApiResponse<MyInfoResponse>
}