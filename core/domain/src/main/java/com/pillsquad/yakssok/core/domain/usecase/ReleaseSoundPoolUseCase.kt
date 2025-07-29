package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.model.AlarmType
import javax.inject.Inject

class ReleaseSoundPoolUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    operator fun invoke(prevAlarm: AlarmType?) {
        prevAlarm?.let {
            soundRepository.stopSound(it)
        }
        soundRepository.release()
    }
}