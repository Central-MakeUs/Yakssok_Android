package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.DeviceRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserDevicesApi {
    @POST("/api/user-devices/devices")
    suspend fun postUserDevices(
        @Body params: DeviceRequest
    ): ApiResponse<Unit>
}