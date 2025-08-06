package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.MyInfoRequest
import com.pillsquad.yakssok.core.network.model.response.InviteCodeResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import com.pillsquad.yakssok.core.network.model.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {
    @GET("/api/users/me")
    suspend fun getMyInfo(): ApiResponse<MyInfoResponse>

    @PUT("/api/users/me")
    suspend fun putMyInfo(
        @Body params: MyInfoRequest
    ): ApiResponse<Unit>

    @GET("/api/users/invite-code")
    suspend fun getMyInviteCode(): ApiResponse<InviteCodeResponse>

    @GET("/api/users")
    suspend fun getUserInfoByInviteCode(
        @Query("inviteCode") inviteCode: String
    ): ApiResponse<UserInfoResponse>

    @DELETE("/api/users")
    suspend fun deleteUser(): ApiResponse<Unit>

    // auth에 들어가야 관념상 맞긴한데, AuthApi는 NoHeader이기에 UserApi에 선언
    @PUT("/api/auth/logout")
    suspend fun putLogout(): ApiResponse<Unit>
}