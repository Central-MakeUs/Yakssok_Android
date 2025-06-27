package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.datasource_api.model.ApiResponse
import com.pillsquad.yakssok.datasource_api.model.UserData
import com.pillsquad.yakssok.datasource_api.remote.UserDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserDataSource @Inject constructor(

) : UserDataSource {
    override suspend fun searchUser(userName: String): ApiResponse<UserData> {
        delay(5000)
        return ApiResponse.Success(UserData(1, userName))
    }
}