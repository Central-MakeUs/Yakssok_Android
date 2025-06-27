package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.core.network.UserApi
import com.pillsquad.yakssok.core.network.mapper.toUserData
import com.pillsquad.yakssok.core.network.util.runRemote
import com.pillsquad.yakssok.datasource_api.model.ApiResponse
import com.pillsquad.yakssok.datasource_api.model.map
import com.pillsquad.yakssok.datasource_api.model.UserData
import com.pillsquad.yakssok.datasource_api.remote.UserDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUserDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun searchUser(
        userName: String
    ): ApiResponse<UserData> {
        return runRemote {
            userApi.searchUser(userName)
        }.map { it.toUserData() }
    }
}