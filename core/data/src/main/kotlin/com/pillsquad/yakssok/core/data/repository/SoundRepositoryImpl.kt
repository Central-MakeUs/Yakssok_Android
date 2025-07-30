package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toSoundType
import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.sound.SoundManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundRepositoryImpl @Inject constructor(
    private val soundManager: SoundManager
) : SoundRepository {
    override fun initSoundPool() = soundManager.reInitIfNeeded()

    override fun playSound(alarmType: AlarmType) =
        soundManager.playSound(alarmType.toSoundType())

    override fun stopSound(alarmType: AlarmType) =
        soundManager.stopSound(alarmType.toSoundType())

    override fun releaseSoundPool() = soundManager.releaseSoundPool()
}