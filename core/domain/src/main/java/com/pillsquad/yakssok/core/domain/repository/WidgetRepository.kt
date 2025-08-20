package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.WidgetSnapshot
import kotlinx.coroutines.flow.Flow

interface WidgetRepository {
    fun snapshotFlow(): Flow<WidgetSnapshot>
    suspend fun save(snapshot: WidgetSnapshot): Result<Unit>
}