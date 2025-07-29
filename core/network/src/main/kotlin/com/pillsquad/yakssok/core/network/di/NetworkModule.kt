package com.pillsquad.yakssok.core.network.di

import com.pillsquad.yakssok.core.network.BuildConfig
import com.pillsquad.yakssok.core.network.calladapter.ApiResponseCallAdapterFactory
import com.pillsquad.yakssok.core.network.interceptor.TokenInterceptor
import com.pillsquad.yakssok.core.network.service.AuthApi
import com.pillsquad.yakssok.core.network.service.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class TokenInterceptorHttpClient

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NoHeaderHttpClient

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class TokenRetrofit

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NoHeaderRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TYPE_JSON = "application/json"

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @TokenInterceptorHttpClient
    @Provides
    @Singleton
    fun provideTokenHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @NoHeaderHttpClient
    @Provides
    @Singleton
    fun provideNoHeaderHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @TokenRetrofit
    @Provides
    @Singleton
    fun provideTokenRetrofit(
        @TokenInterceptorHttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory(TYPE_JSON.toMediaType()))
            .build()

    @NoHeaderRetrofit
    @Provides
    @Singleton
    fun provideNoHeaderRetrofit(
        @NoHeaderHttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(json.asConverterFactory(TYPE_JSON.toMediaType()))
            .build()
}