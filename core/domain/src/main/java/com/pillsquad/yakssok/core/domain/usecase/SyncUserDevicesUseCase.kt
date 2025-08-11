package com.pillsquad.yakssok.core.domain.usecase

import javax.inject.Inject

class SyncUserDevicesUseCase @Inject constructor(
    private val getPushAgreement: GetPushAgreementUseCase,
    private val postUserDevices: PostUserDevicesUseCase
) {
    suspend operator fun invoke(): Result<Unit> = postUserDevices(getPushAgreement())
}