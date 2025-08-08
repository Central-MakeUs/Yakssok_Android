package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.DeviceRequest

interface UserDevicesDataSource {
    suspend fun postUserDevices(params: DeviceRequest): ApiResponse<Unit>
}