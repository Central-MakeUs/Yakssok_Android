package com.pillsquad.yakssok.core.data.di

import com.pillsquad.yakssok.core.data.AuthTokenExpirationHandler
import com.pillsquad.yakssok.core.data.AuthTokenProvider
import com.pillsquad.yakssok.core.network.interceptor.TokenExpirationHandler
import com.pillsquad.yakssok.core.network.interceptor.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    abstract fun bindTokenProvider(tokenProvider: AuthTokenProvider): TokenProvider

    @Binds
    abstract fun bindTokenExpirationHandler(tokenExpirationHandler: AuthTokenExpirationHandler): TokenExpirationHandler
}