package com.pillsquad.yakssok.core.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.android.gms.appset.AppSet
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class DeviceIdProviderImpl @Inject constructor(
    @field:ApplicationContext val context: Context
): DeviceIdProvider {
    override suspend fun getStableDeviceId(): String {
        val appSetId = getAppSetId()

        return appSetId ?: getAndroidIdOrNull() ?: generateAndCacheUuid()
    }

    private suspend fun getAppSetId(): String? = try {
        AppSet.getClient(context).appSetIdInfo.await().id
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private fun generateAndCacheUuid(): String =
        UUID.randomUUID().toString()

    @SuppressLint("HardwareIds") // 폴백에서만 허용
    private fun getAndroidIdOrNull(): String? =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?.takeIf { it.isNotBlank() }
}