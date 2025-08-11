package com.pillsquad.yakssok.core.data.repository

import android.util.Log
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.UserDevicesRepository
import com.pillsquad.yakssok.core.firebase.DeviceIdProvider
import com.pillsquad.yakssok.core.firebase.FcmTokenProvider
import com.pillsquad.yakssok.core.network.datasource.UserDevicesDataSource
import com.pillsquad.yakssok.core.network.di.RetrofitUserDevices
import com.pillsquad.yakssok.core.network.model.request.DeviceRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserDevicesRepositoryImpl @Inject constructor(
    @RetrofitUserDevices private val userDevicesDataSource: UserDevicesDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val deviceIdProvider: DeviceIdProvider,
    private val fcmTokenProvider: FcmTokenProvider
) : UserDevicesRepository {

    override suspend fun postUserDevices(alertOn: Boolean): Result<Unit> {
        val deviceId = userLocalDataSource.deviceIdFlow.firstOrNull()
            ?.takeIf { it.isNotBlank() }
            ?: deviceIdProvider.getStableDeviceId().also { userLocalDataSource.saveDeviceId(it) }

        val fcmToken = userLocalDataSource.fcmTokenFlow.firstOrNull()
            ?.takeIf { it.isNotBlank() }
            ?: fcmTokenProvider.fetchFcmTokenOrNull()
                ?.also { if (it.isNotBlank()) userLocalDataSource.saveFcmToken(it) }

        val request = DeviceRequest(
            deviceId = deviceId,
            fcmToken = fcmToken.orEmpty(),
            alertOn = alertOn
        )

        val result = userDevicesDataSource.postUserDevices(request).toResult(transform = { it })

        result.onSuccess {
            userLocalDataSource.savePushAgreement(alertOn)
        }.onFailure {
            it.printStackTrace()
            Log.e("UserDevicesRepositoryImpl", "postUserDevices: $it")
        }

        return result
    }
}