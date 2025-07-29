package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.sound.model.SoundType

internal fun AlarmType.toSoundType(): SoundType =
    when(this) {
        AlarmType.FEEL_GOOD -> SoundType.FEEL_GOOD
        AlarmType.PILL_SHAKE -> SoundType.PILL_SHAKE
        AlarmType.SCOLD -> SoundType.SCOLD
        AlarmType.CALL -> SoundType.CALL
        AlarmType.VIBRATION -> SoundType.VIBRATION
    }