package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveFcmTokenUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(token: String) = userPreferencesRepository.saveFcmToken(token)
}