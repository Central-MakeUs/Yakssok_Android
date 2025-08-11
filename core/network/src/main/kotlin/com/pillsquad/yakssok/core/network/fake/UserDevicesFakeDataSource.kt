package com.pillsquad.yakssok.core.network.fake

import android.util.Log
import com.pillsquad.yakssok.core.network.datasource.UserDevicesDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.DeviceRequest
import javax.inject.Inject

class UserDevicesFakeDataSource @Inject constructor(

): UserDevicesDataSource {
    override suspend fun postUserDevices(params: DeviceRequest): ApiResponse<Unit> {
        Log.e("UserDevicesFakeDataSource", "postUserDevices: $params")

        return ApiResponse.Failure.UnknownApiError(Throwable("fake error"))
    }
}