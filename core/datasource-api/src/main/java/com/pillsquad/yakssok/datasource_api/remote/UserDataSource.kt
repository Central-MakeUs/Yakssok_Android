package com.pillsquad.yakssok.datasource_api.remote

import com.pillsquad.yakssok.datasource_api.model.ApiResponse
import com.pillsquad.yakssok.datasource_api.model.UserData

interface UserDataSource {
    suspend fun searchUser(userName: String): ApiResponse<UserData>
}