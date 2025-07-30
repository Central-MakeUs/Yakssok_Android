package com.pillsquad.yakssok.core.domain.usecase.sound

import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import javax.inject.Inject

class InitSoundPoolUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    operator fun invoke() {
        soundRepository.initSoundPool()
    }
}