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
    private var soundPool: SoundPool? = null

    private val soundMap: HashMap<SoundType, Int> = HashMap(5)
    private val streamIdMap: HashMap<SoundType, Int> = HashMap(5)

    init {
        initSoundPool()
    }

    fun playSound(soundType: SoundType) {
        val pool = soundPool ?: return
        val soundId = soundMap[soundType] ?: return
        val streamId = pool.play(soundId, 1f, 1f, 0, 0, 1f)
        streamIdMap[soundType] = streamId
    }

    fun stopSound(soundType: SoundType) {
        val pool = soundPool ?: return
        val streamId = streamIdMap[soundType] ?: return
        pool.stop(streamId)
        streamIdMap.remove(soundType)
    }

    fun reInitIfNeeded() {
        if (soundPool == null) {
            initSoundPool()
        }
    }

    private fun initSoundPool() {
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()
        loadSounds()
    }

    private fun loadSounds() {
        val pool = soundPool ?: return

        soundMap[SoundType.FEEL_GOOD] = pool.load(context, R.raw.feel_good, 1)
        soundMap[SoundType.PILL_SHAKE] = pool.load(context, R.raw.pill_shake, 1)
        soundMap[SoundType.SCOLD] = pool.load(context, R.raw.scold, 1)
        soundMap[SoundType.CALL] = pool.load(context, R.raw.call, 1)
        soundMap[SoundType.VIBRATION] = pool.load(context, R.raw.vibration, 1)
    }
}