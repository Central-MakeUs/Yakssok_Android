package com.pillsquad.yakssok.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pillsquad.yakssok.datastore.di.WidgetPreferences
import com.pillsquad.yakssok.datastore.model.WidgetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetPreferences @Inject constructor(
    @WidgetPreferences private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val SUB = stringPreferencesKey("sub")
        private val PROGRESS = stringPreferencesKey("progress")
        private val NEXT_ROUTINE = intPreferencesKey("next_routine")
    }

    val snapshotFlow: Flow<WidgetEntity> =
        dataStore.data
            .map { p ->
                WidgetEntity(
                    sub = p[SUB] ?: "오늘은 없어요!",
                    progress = p[PROGRESS] ?: "0/0회",
                    nextRoutine = p[NEXT_ROUTINE]
                )
            }
            .distinctUntilChanged()

    suspend fun save(snapshot: WidgetEntity) {
        dataStore.edit { p ->
            p[SUB] = snapshot.sub
            p[PROGRESS] = snapshot.progress
            snapshot.nextRoutine?.let { p[NEXT_ROUTINE] = it } ?: p.remove(NEXT_ROUTINE)
        }
    }
}