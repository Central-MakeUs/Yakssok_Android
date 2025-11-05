package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetUserTutorialCompleteUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke() = userPreferencesRepository.getTutorialComplete()
}