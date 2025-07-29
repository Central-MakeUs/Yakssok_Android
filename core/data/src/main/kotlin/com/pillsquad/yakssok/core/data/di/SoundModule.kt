package com.pillsquad.yakssok.core.data.di

import android.content.Context
import com.pillsquad.yakssok.core.data.sound.SoundManager
import com.pillsquad.yakssok.core.domain.repository.SoundRepository
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
    ): SoundRepository = SoundManager(context)
}