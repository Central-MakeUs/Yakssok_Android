package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.InviteCodeResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import com.pillsquad.yakssok.core.network.model.response.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("/api/users/me")
    suspend fun getMyInfo(): ApiResponse<MyInfoResponse>

    @GET("/api/users/invite-code")
    suspend fun getMyInviteCode(): ApiResponse<InviteCodeResponse>

    @GET("/api/users")
    suspend fun getUserInfoByInviteCode(
        @Query("inviteCode") inviteCode: String
    ): ApiResponse<UserInfoResponse>
}