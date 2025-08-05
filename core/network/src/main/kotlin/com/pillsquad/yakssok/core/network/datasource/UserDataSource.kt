package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.InviteCodeResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import com.pillsquad.yakssok.core.network.model.response.UserInfoResponse

interface UserDataSource {

    suspend fun getMyInfo(): ApiResponse<MyInfoResponse>
    suspend fun getMyInviteCode(): ApiResponse<InviteCodeResponse>
    suspend fun getUserInfoByInviteCode(inviteCode: String): ApiResponse<UserInfoResponse>
    suspend fun deleteUser(): ApiResponse<Unit>
    suspend fun putLogout(): ApiResponse<Unit>
}