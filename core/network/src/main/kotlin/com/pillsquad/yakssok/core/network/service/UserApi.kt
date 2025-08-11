package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.MyInfoRequest
import com.pillsquad.yakssok.core.network.model.request.UserInitialRequest
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

    // 유저 정보 수정
    @PUT("/api/users/me")
    suspend fun putMyInfo(
        @Body params: MyInfoRequest
    ): ApiResponse<Unit>

    @GET("/api/users/invite-code")
    suspend fun getMyInviteCode(): ApiResponse<InviteCodeResponse>

    @PUT("/api/users/init")
    suspend fun putUserInitial(
        @Body params: UserInitialRequest
    ): ApiResponse<Unit>

    @GET("/api/users")
    suspend fun getUserInfoByInviteCode(
        @Query("inviteCode") inviteCode: String
    ): ApiResponse<UserInfoResponse>

    @DELETE("/api/users")
    suspend fun deleteUser(): ApiResponse<Unit>
}