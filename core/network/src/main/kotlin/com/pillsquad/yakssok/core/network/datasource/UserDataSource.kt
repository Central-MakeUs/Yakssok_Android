package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse

interface UserDataSource {

    suspend fun getMyInfo(): ApiResponse<MyInfoResponse>
}