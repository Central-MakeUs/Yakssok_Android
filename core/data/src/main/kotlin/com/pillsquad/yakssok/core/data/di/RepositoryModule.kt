package com.pillsquad.yakssok.core.data.di

import com.pillsquad.yakssok.core.data.UserRepository
import com.pillsquad.yakssok.core.data.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

}