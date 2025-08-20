package com.pillsquad.yakssok.datastore

import com.pillsquad.yakssok.datastore.model.WidgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetDataSource @Inject constructor(
    private val widgetPreferences: WidgetPreferences
) {
    fun snapshotFlow(): Flow<WidgetEntity> = widgetPreferences.snapshotFlow
    suspend fun save(snapshot: WidgetEntity) = widgetPreferences.save(snapshot)
}