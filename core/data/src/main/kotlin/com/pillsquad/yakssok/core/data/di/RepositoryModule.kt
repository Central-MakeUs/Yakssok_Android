package com.pillsquad.yakssok.core.data.di

import com.pillsquad.yakssok.core.data.repository.MedicationRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.UserRepositoryImpl
import com.pillsquad.yakssok.core.data.sound.SoundRepositoryImpl
import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindMedicationRepository(
        medicationRepository: MedicationRepositoryImpl
    ): MedicationRepository

    @Binds
    abstract fun bindSoundRepository(
        soundRepository: SoundRepositoryImpl
    ): SoundRepository
}