package com.pillsquad.yakssok.core.network.di

import com.pillsquad.yakssok.core.network.service.AuthApi
import com.pillsquad.yakssok.core.network.service.MedicationApi
import com.pillsquad.yakssok.core.network.service.TokenApi
import com.pillsquad.yakssok.core.network.service.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideTokenApi(
        @NoHeaderRetrofit retrofit: Retrofit
    ): TokenApi = retrofit.create(TokenApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitApi(
        @TokenRetrofit retrofit: Retrofit
    ): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(
        @TokenRetrofit retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideMedicationApi(
        @TokenRetrofit retrofit: Retrofit
    ): MedicationApi = retrofit.create(MedicationApi::class.java)
}