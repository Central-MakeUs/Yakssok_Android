package com.pillsquad.yakssok.core.domain.usecase.sound

import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.model.AlarmType
import javax.inject.Inject

class StopAlarmSoundUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    operator fun invoke(alarmType: AlarmType) {
        soundRepository.stopSound(alarmType)
    }
}