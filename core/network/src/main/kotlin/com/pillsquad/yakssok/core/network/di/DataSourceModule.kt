package com.pillsquad.yakssok.core.network.di

import com.pillsquad.yakssok.core.network.retrofit.FakeUserDataSource
import com.pillsquad.yakssok.core.network.retrofit.RetrofitUserDataSource
import com.pillsquad.yakssok.datasource_api.remote.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Qualifier

// 단순히 구현체 - interface 연결이면 Binds 사용
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Named("RetrofitUser")
    abstract fun bindUserDataSource(
        retrofitUserDataSource: RetrofitUserDataSource
    ): UserDataSource

    @Binds
    @Named("FakeUser")
    abstract fun bindFakeUserDataSource(
        fakeUserDataSource: FakeUserDataSource
    ): UserDataSource

}
