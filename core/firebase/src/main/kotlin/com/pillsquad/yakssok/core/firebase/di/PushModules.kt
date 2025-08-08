package com.pillsquad.yakssok.core.firebase.di

import android.content.Context
import com.pillsquad.yakssok.core.firebase.DeviceIdProvider
import com.pillsquad.yakssok.core.firebase.DeviceIdProviderImpl
import com.pillsquad.yakssok.core.firebase.FcmTokenProvider
import com.pillsquad.yakssok.core.firebase.FcmTokenProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PushBindings {
    @Binds @Singleton
    abstract fun bindDeviceIdProvider(impl: DeviceIdProviderImpl): DeviceIdProvider

    @Binds @Singleton
    abstract fun bindFcmTokenProvider(impl: FcmTokenProviderImpl): FcmTokenProvider
}