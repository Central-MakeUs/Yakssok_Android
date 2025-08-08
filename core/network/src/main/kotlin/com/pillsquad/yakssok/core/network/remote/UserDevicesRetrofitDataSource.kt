package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.UserDevicesDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.DeviceRequest
import com.pillsquad.yakssok.core.network.service.UserDevicesApi
import javax.inject.Inject

class UserDevicesRetrofitDataSource @Inject constructor(
    private val userDevicesApi: UserDevicesApi
): UserDevicesDataSource {

    override suspend fun postUserDevices(params: DeviceRequest): ApiResponse<Unit> =
        userDevicesApi.postUserDevices(params)
}