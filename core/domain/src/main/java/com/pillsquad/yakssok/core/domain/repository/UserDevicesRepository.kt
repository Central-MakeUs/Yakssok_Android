package com.pillsquad.yakssok.core.domain.repository

interface UserDevicesRepository {

    suspend fun postUserDevices(alertOn: Boolean): Result<Unit>
}