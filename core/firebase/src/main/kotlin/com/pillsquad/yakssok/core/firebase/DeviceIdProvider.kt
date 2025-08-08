package com.pillsquad.yakssok.core.firebase

interface DeviceIdProvider {
    suspend fun getStableDeviceId(): String
}