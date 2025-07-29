package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.model.AlarmType
import javax.inject.Inject

class PlayAlarmSoundUseCase @Inject constructor(
    private val soundManager: SoundRepository
) {
    operator fun invoke(curAlarm: AlarmType, prevAlarm: AlarmType?) {
        if (curAlarm == prevAlarm) return
        prevAlarm?.let {
            soundManager.stopSound(it)
        }
        soundManager.playSound(curAlarm)
    }
}