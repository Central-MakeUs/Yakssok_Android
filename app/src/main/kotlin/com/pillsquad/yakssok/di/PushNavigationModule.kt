package com.pillsquad.yakssok.di

import com.pillsquad.yakssok.core.push.PushNavigation
import com.pillsquad.yakssok.AppPushNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PushNavigationModule {
    @Binds
    @Singleton
    abstract fun bindPushNavigation(impl: AppPushNavigation): PushNavigation
}