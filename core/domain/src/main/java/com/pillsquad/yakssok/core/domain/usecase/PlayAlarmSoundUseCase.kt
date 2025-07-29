package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.model.AlarmType
import javax.inject.Inject

class PlayAlarmSoundUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    operator fun invoke(curAlarm: AlarmType, prevAlarm: AlarmType?) {
        if (curAlarm == prevAlarm) return
        prevAlarm?.let {
            soundRepository.stopSound(it)
        }
        soundRepository.playSound(curAlarm)
    }
}