package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.NotificationDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.AlarmListResponse
import com.pillsquad.yakssok.core.network.service.NotificationApi
import javax.inject.Inject

class NotificationRetrofitDataSource @Inject constructor(
    private val notificationApi: NotificationApi
): NotificationDataSource {
    override suspend fun fetchAlarms(
        startKey: String?,
        perPage: Int
    ): ApiResponse<AlarmListResponse> {
        val lastId = startKey?.toIntOrNull() ?: Int.MAX_VALUE
        return notificationApi.getAlarmList(lastId, perPage)
    }
}