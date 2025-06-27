package com.pillsquad.yakssok.di

import com.pillsquad.yakssok.domain.repository.UserRepository
import com.pillsquad.yakssok.domain.usecase.GetUserByGymNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetAlbumsUseCase(impl: UserRepository): GetUserByGymNameUseCase =
        GetUserByGymNameUseCase(impl)
}