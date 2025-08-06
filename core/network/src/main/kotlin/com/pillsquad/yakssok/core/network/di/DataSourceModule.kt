package com.pillsquad.yakssok.core.network.di

import android.content.Context
import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.datasource.FeedbackDataSource
import com.pillsquad.yakssok.core.network.datasource.FriendDataSource
import com.pillsquad.yakssok.core.network.datasource.ImageDataSource
import com.pillsquad.yakssok.core.network.datasource.MedicationDataSource
import com.pillsquad.yakssok.core.network.datasource.RoutineDataSource
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.remote.AuthRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.FeedbackRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.FriendRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.ImageRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.MedicationRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.RoutineRetrofitDataSource
import com.pillsquad.yakssok.core.network.remote.UserRetrofitDataSource
import com.pillsquad.yakssok.core.network.service.ImageApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    companion object {
        @Provides
        fun provideImageDataSource(
            @ApplicationContext context: Context,
            imageApi: ImageApi
        ): ImageDataSource {
            return ImageRetrofitDataSource(context, imageApi)
        }
    }
}
