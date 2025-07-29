package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.AlarmType

interface SoundRepository {
    fun playSound(alarmType: AlarmType)
    fun stopSound(alarmType: AlarmType)
    fun release()
}