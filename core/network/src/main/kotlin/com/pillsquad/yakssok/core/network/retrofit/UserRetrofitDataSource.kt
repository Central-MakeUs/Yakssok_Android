package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.core.network.api.UserApi
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import javax.inject.Inject

class UserRetrofitDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun getMyInfo(): ApiResponse<MyInfoResponse> =
        userApi.getMyInfo()
}