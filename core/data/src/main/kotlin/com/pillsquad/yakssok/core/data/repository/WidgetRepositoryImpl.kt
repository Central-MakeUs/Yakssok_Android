package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toWidgetEntity
import com.pillsquad.yakssok.core.data.mapper.toWidgetSnapShot
import com.pillsquad.yakssok.core.domain.repository.WidgetRepository
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import com.pillsquad.yakssok.datastore.WidgetDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WidgetRepositoryImpl @Inject constructor(
    private val local: WidgetDataSource
) : WidgetRepository {
    override fun snapshotFlow(): Flow<WidgetSnapshot> =
        local.snapshotFlow().map { it.toWidgetSnapShot() }

    override suspend fun save(snapshot: WidgetSnapshot): Result<Unit> = runCatching {
        local.save(snapshot.toWidgetEntity())
    }
}