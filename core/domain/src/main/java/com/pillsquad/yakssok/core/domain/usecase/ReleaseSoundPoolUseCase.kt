package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import javax.inject.Inject

class ReleaseSoundPoolUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    operator fun invoke() {
        soundRepository.releaseSoundPool()
    }
}