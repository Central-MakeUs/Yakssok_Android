package com.pillsquad.yakssok.datastore

import androidx.datastore.core.DataStore
import com.pillsquad.yakssok.datastore.di.WidgetPreferences
import com.pillsquad.yakssok.datastore.model.WidgetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetPreferences @Inject constructor(
    @WidgetPreferences private val dataStore: DataStore<WidgetEntity>
) {
    val snapshotFlow: Flow<WidgetEntity> = dataStore.data.distinctUntilChanged()

    suspend fun save(snapshot: WidgetEntity) {
        dataStore.updateData { snapshot }
    }
}