package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WidgetRepository {
    fun snapshotFlow(): Flow<WidgetSnapshot>
    suspend fun saveFromUserCache(cache: UserCache, day: LocalDate): Result<Unit>
}