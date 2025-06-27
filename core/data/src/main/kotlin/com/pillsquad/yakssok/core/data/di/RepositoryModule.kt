package com.pillsquad.yakssok.core.data.di

import com.pillsquad.yakssok.core.data.UserRepositoryImpl
import com.pillsquad.yakssok.domain.repository.UserRepository
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

}