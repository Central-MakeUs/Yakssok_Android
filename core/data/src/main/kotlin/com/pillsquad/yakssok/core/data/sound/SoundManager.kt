package com.pillsquad.yakssok.core.data.sound

import android.content.Context
import android.media.SoundPool
import com.pillsquad.yakssok.core.data.R
import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.model.AlarmType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SoundRepository {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    private val soundMap: HashMap<AlarmType, Int> = HashMap(5)
    private val streamIdMap: HashMap<AlarmType, Int> = HashMap(5)

    init {
       loadSounds()
    }

    override fun playSound(alarmType: AlarmType) {
        val soundId = soundMap[alarmType] ?: return
        val streamId = soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        streamIdMap[alarmType] = streamId
    }

    override fun stopSound(alarmType: AlarmType) {
        val streamId = streamIdMap[alarmType] ?: return
        soundPool.stop(streamId)
        streamIdMap.remove(alarmType)
    }

    override fun release() {
        soundPool.release()
        streamIdMap.clear()
    }

    private fun loadSounds() {
        soundMap[AlarmType.FEEL_GOOD] = soundPool.load(context, R.raw.feel_good, 1)
        soundMap[AlarmType.PILL_SHAKE] = soundPool.load(context, R.raw.pill_shake, 1)
        soundMap[AlarmType.SCOLD] = soundPool.load(context, R.raw.scold, 1)
        soundMap[AlarmType.CALL] = soundPool.load(context, R.raw.call, 1)
        soundMap[AlarmType.VIBRATION] = soundPool.load(context, R.raw.vibration, 1)
    }
}