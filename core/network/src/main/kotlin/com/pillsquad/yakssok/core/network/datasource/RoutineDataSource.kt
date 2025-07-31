package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.RoutineResponse

interface RoutineDataSource {

    suspend fun getMyRoutine(startDate: String, endDate: String): ApiResponse<RoutineResponse>
    suspend fun getFriendRoutine(startDate: String, endDate: String, friendsId: Int): ApiResponse<RoutineResponse>
    suspend fun putTakeRoutine(scheduleId: Int): ApiResponse<Unit>
}