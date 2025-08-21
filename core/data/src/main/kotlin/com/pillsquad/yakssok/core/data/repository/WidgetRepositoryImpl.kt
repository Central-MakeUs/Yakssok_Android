package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toWidgetEntity
import com.pillsquad.yakssok.core.data.mapper.toWidgetSnapShot
import com.pillsquad.yakssok.core.domain.repository.WidgetRepository
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.model.WidgetItem
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import com.pillsquad.yakssok.datastore.WidgetDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class WidgetRepositoryImpl @Inject constructor(
    private val local: WidgetDataSource
) : WidgetRepository {
    override fun snapshotFlow(): Flow<WidgetSnapshot> =
        local.snapshotFlow().map { it.toWidgetSnapShot() }

    override suspend fun saveFromUserCache(cache: UserCache, day: LocalDate): Result<Unit> {
        return runCatching {
            val rows = cache.routineCache[day].orEmpty()
                .sortedBy { it.intakeTime }
                .map {  r ->
                    WidgetItem(
                        routineId = r.routineId ?: 0,
                        intakeTime = r.intakeTime.toString(),
                        medicationName = r.medicationName,
                        isTaken = cache.takenCache[day] == true
                    )
                }
            val total = rows.size
            val taken = rows.count { it.isTaken }
            val snap = WidgetSnapshot(rows, "${taken}/${total}회")

            local.save(snap.toWidgetEntity())
        }
    }
}