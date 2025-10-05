package com.pillsquad.yakssok.core.firebase.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.pillsquad.yakssok.core.firebase.DeviceIdProvider
import com.pillsquad.yakssok.core.firebase.DeviceIdProviderImpl
import com.pillsquad.yakssok.core.firebase.FcmTokenProvider
import com.pillsquad.yakssok.core.firebase.FcmTokenProviderImpl
import com.pillsquad.yakssok.core.firebase.RemoteConfigProvider
import com.pillsquad.yakssok.core.firebase.RemoteConfigProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModules {
    @Binds
    @Singleton
    abstract fun bindDeviceIdProvider(impl: DeviceIdProviderImpl): DeviceIdProvider

    @Binds
    @Singleton
    abstract fun bindFcmTokenProvider(impl: FcmTokenProviderImpl): FcmTokenProvider

    @Binds
    @Singleton
    abstract fun bindRemoteConfigProvider(impl: RemoteConfigProviderImpl): RemoteConfigProvider

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            remoteConfig.setConfigSettingsAsync(configSettings)

            return remoteConfig
        }
    }
}