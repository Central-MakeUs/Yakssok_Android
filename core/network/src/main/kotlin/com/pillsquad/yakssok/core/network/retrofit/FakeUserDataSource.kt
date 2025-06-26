package com.pillsquad.yakssok.core.network.retrofit

import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.UserResponse
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FakeUserDataSource @Inject constructor(

) : UserDataSource {
    override suspend fun searchUser(userName: String): ApiResponse<UserResponse> {
        delay(5000)
        return ApiResponse.Failure.HttpError(404, "Not Found", "")
    }
}