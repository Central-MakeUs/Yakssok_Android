package com.pillsquad.yakssok.core.network

import com.pillsquad.yakssok.core.network.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    // example
    @GET("/api/user/search/all")
    suspend fun searchUser(
        @Query("userName") userName: String
    ): Response<UserResponse>

}