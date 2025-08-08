package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.UserDevicesRepository
import com.pillsquad.yakssok.core.firebase.DeviceIdProvider
import com.pillsquad.yakssok.core.firebase.FcmTokenProvider
import com.pillsquad.yakssok.core.network.datasource.UserDevicesDataSource
import com.pillsquad.yakssok.core.network.model.request.DeviceRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserDevicesRepositoryImpl @Inject constructor(
    private val userDevicesDataSource: UserDevicesDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val deviceIdProvider: DeviceIdProvider,
    private val fcmTokenProvider: FcmTokenProvider
): UserDevicesRepository {

    override suspend fun postUserDevices(alertOn: Boolean): Result<Unit> {
        val deviceId = userLocalDataSource.deviceIdFlow.firstOrNull()
            ?: deviceIdProvider.getStableDeviceId().also { userLocalDataSource.saveDeviceId(it) }
        val fcmToken = userLocalDataSource.fcmTokenFlow.firstOrNull()
            ?: fcmTokenProvider.fetchFcmTokenOrNull()?.also { userLocalDataSource.saveFcmToken(it) }

        val request = DeviceRequest(
            deviceId = deviceId,
            fcmToken = fcmToken.orEmpty(),
            alertOn = alertOn
        )

        return userDevicesDataSource.postUserDevices(request).toResult()
    }
}