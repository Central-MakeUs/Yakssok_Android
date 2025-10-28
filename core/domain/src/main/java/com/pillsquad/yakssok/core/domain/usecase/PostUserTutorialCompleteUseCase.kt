package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class PostUserTutorialCompleteUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke() = userPreferencesRepository.saveTutorialComplete()
}