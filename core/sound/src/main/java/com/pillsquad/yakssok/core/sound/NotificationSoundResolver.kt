package com.pillsquad.yakssok.core.sound

import android.content.Context
import android.net.Uri
import com.pillsquad.yakssok.core.sound.model.SoundType
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri

@Singleton
class NotificationSoundResolver @Inject constructor(
    private val context: Context
) {

    fun uriFor(type: SoundType): Uri? = when(type) {
        SoundType.FEEL_GOOD -> res("feel_good")
        SoundType.PILL_SHAKE -> res("pill_shake")
        SoundType.SCOLD -> res("scold")
        SoundType.CALL -> res("call")
        SoundType.VIBRATION -> res("vibration")
    }

    private fun res(name: String): Uri =
        "android.resource://${context.packageName}/raw/$name".toUri()
}