package com.pillsquad.yakssok.core.data.di

import android.content.Context
import com.pillsquad.yakssok.core.data.repository.AuthRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.FeedbackRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.FriendRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.ImageRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.MedicationRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.NotificationRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.RoutineRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.SoundRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.UserDevicesRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.UserPreferencesRepositoryImpl
import com.pillsquad.yakssok.core.data.repository.UserRepositoryImpl
import com.pillsquad.yakssok.core.domain.repository.AuthRepository
import com.pillsquad.yakssok.core.domain.repository.FeedbackRepository
import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.domain.repository.ImageRepository
import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import com.pillsquad.yakssok.core.domain.repository.NotificationRepository
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.domain.repository.SoundRepository
import com.pillsquad.yakssok.core.domain.repository.UserDevicesRepository
import com.pillsquad.yakssok.core.domain.repository.UserPreferencesRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.network.datasource.ImageDataSource
import com.pillsquad.yakssok.core.network.datasource.UserDevicesDataSource
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Binds
    abstract fun bindFeedbackRepository(
        feedbackRepository: FeedbackRepositoryImpl
    ): FeedbackRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepository: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun bindUserDevicesRepository(
        userDevicesRepository: UserDevicesRepositoryImpl
    ): UserDevicesRepository

    @Binds @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepository: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    companion object {
        @Provides
        @Singleton
        fun provideImageRepository(
            @ApplicationContext context: Context,
            imageDataSource: ImageDataSource,
            localDataSource: UserLocalDataSource
        ): ImageRepository {
            return ImageRepositoryImpl(context, imageDataSource, localDataSource)
        }
    }
}