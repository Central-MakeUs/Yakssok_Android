package com.pillsquad.yakssok.core.network.di

import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.datasource.FeedbackDataSource
import com.pillsquad.yakssok.core.network.datasource.FriendDataSource
import com.pillsquad.yakssok.core.network.datasource.MedicationDataSource
import com.pillsquad.yakssok.core.network.datasource.RoutineDataSource
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.remote.AuthRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.FeedbackRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.FriendRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.MedicationRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.RoutineRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.UserRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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

    @Binds
    abstract fun bindMedicationDataSource(
        medicationRetrofitDataSource: MedicationRetrofitDataSource
    ): MedicationDataSource

    @Binds
    abstract fun bindFriendDataSource(
        friendRetrofitDataSource: FriendRetrofitDataSource
    ): FriendDataSource

    @Binds
    abstract fun bindRoutineDataSource(
        routineRetrofitDataSource: RoutineRetrofitDataSource
    ): RoutineDataSource

    @Binds
    abstract fun bindFeedbackDataSource(
        feedbackRetrofitDataSource: FeedbackRetrofitDataSource
    ): FeedbackDataSource
}
