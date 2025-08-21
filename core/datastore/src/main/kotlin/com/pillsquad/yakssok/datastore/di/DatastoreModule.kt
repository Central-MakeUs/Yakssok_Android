package com.pillsquad.yakssok.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.pillsquad.yakssok.datastore.model.WidgetEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UserPreferences

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class WidgetPreferences

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @UserPreferences
    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.dataStoreFile("user_prefs.preferences_pb")
        }

    @WidgetPreferences
    @Provides
    @Singleton
    fun provideWidgetPreferences(
        @ApplicationContext context: Context
    ): DataStore<WidgetEntity> =
        DataStoreFactory.create(
            serializer = WidgetEntitySerializer,
            corruptionHandler = ReplaceFileCorruptionHandler { WidgetEntity() },
            produceFile = { context.dataStoreFile("widget_prefs.json") }
        )
}