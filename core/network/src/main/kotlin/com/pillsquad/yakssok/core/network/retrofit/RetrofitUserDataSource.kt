package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.core.network.api.UserApi
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUserDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun searchUser(userName: String): ApiResponse<UserResponse> =
        userApi.searchUser(userName)
}