package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.core.network.UserApi
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.UserResponse
import com.pillsquad.yakssok.core.network.util.runRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUserDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun searchUser(
        userName: String
    ): ApiResponse<UserResponse> {
        return runRemote {
            userApi.searchUser(userName)
        }
    }
}