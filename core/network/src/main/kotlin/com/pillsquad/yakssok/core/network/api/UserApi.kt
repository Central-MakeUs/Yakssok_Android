package com.pillsquad.yakssok.core.network.api

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    // example
    @GET("user")
    suspend fun searchUser(
        @Query("name") name: String
    ): ApiResponse<UserResponse>

}