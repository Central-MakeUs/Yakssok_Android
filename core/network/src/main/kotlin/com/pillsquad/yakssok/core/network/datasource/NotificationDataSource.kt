package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.AlarmListResponse

interface NotificationDataSource {
    suspend fun fetchAlarms(startKey: String?, perPage: Int): ApiResponse<AlarmListResponse>
}