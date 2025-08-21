package com.pillsquad.yakssok.core.domain.usecase.widget

import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.domain.repository.WidgetRepository
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.model.WidgetItem
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class SyncWidgetSnapshotUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(
        today: LocalDate = LocalDate.today(),
    ): Result<WidgetSnapshot> =
        routineRepository.getMyRoutine()
            .mapCatching { userCache ->
                val snap = computeSnapshot(userCache, LocalDate.today())
                widgetRepository.saveFromUserCache(userCache, today).getOrThrow()
                snap
            }
}

internal fun computeSnapshot(
    cache: UserCache,
    day: LocalDate
): WidgetSnapshot {
    val rows = cache.routineCache[day].orEmpty()
        .sortedBy { it.intakeTime }
        .map { r ->
            WidgetItem(
                routineId = r.routineId ?: 0,
                intakeTime = r.intakeTime.toString(),
                medicationName = r.medicationName,
                isTaken = r.isTaken
            )
        }
    val total = rows.size
    val taken = rows.count { it.isTaken }
    val snap = WidgetSnapshot(rows, "${taken}/${total}회")

    return snap
}