package com.pillsquad.yakssok.core.sound

import android.content.Context
import android.media.SoundPool
import com.pillsquad.yakssok.core.sound.model.SoundType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    private val soundMap: HashMap<SoundType, Int> = HashMap(5)
    private val streamIdMap: HashMap<SoundType, Int> = HashMap(5)

    init {
        loadSounds()
    }

    fun playSound(soundType: SoundType) {
        val soundId = soundMap[soundType] ?: return
        val streamId = soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        streamIdMap[soundType] = streamId
    }

    fun stopSound(soundType: SoundType) {
        val streamId = streamIdMap[soundType] ?: return
        soundPool.stop(streamId)
        streamIdMap.remove(soundType)
    }

    fun release() {
        soundPool.release()
        streamIdMap.clear()
    }

    private fun loadSounds() {
        soundMap[SoundType.FEEL_GOOD] = soundPool.load(context, R.raw.feel_good, 1)
        soundMap[SoundType.PILL_SHAKE] = soundPool.load(context, R.raw.pill_shake, 1)
        soundMap[SoundType.SCOLD] = soundPool.load(context, R.raw.scold, 1)
        soundMap[SoundType.CALL] = soundPool.load(context, R.raw.call, 1)
        soundMap[SoundType.VIBRATION] = soundPool.load(context, R.raw.vibration, 1)
    }
}