package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.AlarmListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApi {
    @GET("/api/notifications")
    suspend fun getAlarmList(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): ApiResponse<AlarmListResponse>
}