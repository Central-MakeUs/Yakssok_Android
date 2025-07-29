package com.pillsquad.yakssok.core.sound.di

import android.content.Context
import com.pillsquad.yakssok.core.sound.SoundManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundModule {

    @Provides
    @Singleton
    fun provideSoundManager(
        @ApplicationContext context: Context,
    ): SoundManager = SoundManager(context)
}