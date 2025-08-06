package com.pillsquad.yakssok.core.network.di

import com.pillsquad.yakssok.core.network.service.AuthApi
import com.pillsquad.yakssok.core.network.service.FeedbackApi
import com.pillsquad.yakssok.core.network.service.FriendApi
import com.pillsquad.yakssok.core.network.service.ImageApi
import com.pillsquad.yakssok.core.network.service.MedicationApi
import com.pillsquad.yakssok.core.network.service.RoutineApi
import com.pillsquad.yakssok.core.network.service.TokenApi
import com.pillsquad.yakssok.core.network.service.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
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

    @Provides
    @Singleton
    fun provideFriendApi(
        @TokenRetrofit retrofit: Retrofit
    ): FriendApi = retrofit.create(FriendApi::class.java)

    @Provides
    @Singleton
    fun provideRoutineApi(
        @TokenRetrofit retrofit: Retrofit
    ): RoutineApi = retrofit.create(RoutineApi::class.java)

    @Provides
    @Singleton
    fun provideFeedbackApi(
        @TokenRetrofit retrofit: Retrofit
    ): FeedbackApi = retrofit.create(FeedbackApi::class.java)

    @Provides
    @Singleton
    fun provideImageApi(
        @TokenRetrofit retrofit: Retrofit
    ): ImageApi = retrofit.create(ImageApi::class.java)
}