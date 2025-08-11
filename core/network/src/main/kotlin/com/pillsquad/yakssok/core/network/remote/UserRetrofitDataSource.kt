package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.service.UserApi
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.MyInfoRequest
import com.pillsquad.yakssok.core.network.model.request.UserInitialRequest
import com.pillsquad.yakssok.core.network.model.response.InviteCodeResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import com.pillsquad.yakssok.core.network.model.response.UserInfoResponse
import javax.inject.Inject

class UserRetrofitDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun getMyInfo(): ApiResponse<MyInfoResponse> =
        userApi.getMyInfo()

    override suspend fun putMyInfo(params: MyInfoRequest): ApiResponse<Unit> =
        userApi.putMyInfo(params)

    override suspend fun putUserInitial(params: UserInitialRequest): ApiResponse<Unit> =
        userApi.putUserInitial(params)

    override suspend fun getMyInviteCode(): ApiResponse<InviteCodeResponse> =
        userApi.getMyInviteCode()

    override suspend fun getUserInfoByInviteCode(inviteCode: String): ApiResponse<UserInfoResponse> =
        userApi.getUserInfoByInviteCode(inviteCode)

    override suspend fun deleteUser(): ApiResponse<Unit> = userApi.deleteUser()
}