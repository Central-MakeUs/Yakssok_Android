package com.pillsquad.yakssok.core.data.di

import com.pillsquad.yakssok.core.data.repository.FriendRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.MedicationRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.AuthRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.RoutineRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.SoundRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.UserRepositoryImpl
import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.domain.repository.AuthRepository
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindMedicationRepository(
        medicationRepository: MedicationRepositoryImpl
    ): MedicationRepository

    @Binds
    abstract fun bindSoundRepository(
        soundRepository: SoundRepositoryImpl
    ): SoundRepository

    @Binds
    abstract fun bindFriendRepository(
        friendRepository: FriendRepositoryImpl
    ): FriendRepository

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindRoutineRepository(
        routineRepository: RoutineRepositoryImpl
    ): RoutineRepository
}