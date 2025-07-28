package com.pillsquad.yakssok.core.network.di

import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.remote.AuthRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.UserRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// 단순히 구현체 - interface 연결이면 Binds 사용
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAuthDataSource(
        authRetrofitDataSource: AuthRetrofitDataSource
    ): AuthDataSource

    @Binds
    abstract fun bindUserDataSource(
        userRetrofitDataSource: UserRetrofitDataSource
    ): UserDataSource
}
