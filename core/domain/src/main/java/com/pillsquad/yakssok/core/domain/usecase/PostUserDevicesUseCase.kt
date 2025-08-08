package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserDevicesRepository
import javax.inject.Inject

class PostUserDevicesUseCase @Inject constructor(
    private val userDevicesRepository: UserDevicesRepository
) {
    suspend operator fun invoke(alertOn: Boolean): Result<Unit> {
        return userDevicesRepository.postUserDevices(alertOn)
    }
}