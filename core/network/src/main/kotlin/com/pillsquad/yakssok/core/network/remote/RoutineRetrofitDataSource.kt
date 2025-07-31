package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.RoutineDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.RoutineResponse
import com.pillsquad.yakssok.core.network.service.RoutineApi
import javax.inject.Inject

class RoutineRetrofitDataSource @Inject constructor(
    private val routineApi: RoutineApi
) : RoutineDataSource {
    override suspend fun getMyRoutine(
        startDate: String,
        endDate: String
    ): ApiResponse<RoutineResponse> = routineApi.getMyRoutine(startDate, endDate)

    override suspend fun getFriendRoutine(
        startDate: String,
        endDate: String,
        friendsId: Int
    ): ApiResponse<RoutineResponse> = routineApi.getFriendRoutine(startDate, endDate, friendsId)

    override suspend fun putTakeRoutine(scheduleId: Int): ApiResponse<Unit> =
        routineApi.putTakeRoutine(scheduleId)
}