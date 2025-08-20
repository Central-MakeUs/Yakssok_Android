package com.pillsquad.yakssok.core.domain.usecase.widget

import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.domain.repository.WidgetRepository
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class SyncWidgetSnapshotUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(): Result<WidgetSnapshot> =
        routineRepository.getMyRoutine()
            .mapCatching { userCache ->
                val snap = computeSnapshot(LocalTime.now(), userCache, LocalDate.today())
                widgetRepository.save(snap).getOrThrow()
                snap
            }
}

internal fun computeSnapshot(
    now: LocalTime,
    cache: UserCache,
    day: LocalDate
): WidgetSnapshot {
    val items = cache.routineCache[day].orEmpty()
    if (items.isEmpty()) return WidgetSnapshot("오늘은 없어요!", "0/0회", null)

    val total = items.size
    val taken = items.count { it.isTaken }

    val future = items
        .asSequence()
        .filter { !it.isTaken && it.intakeTime > now }
        .minByOrNull { it.intakeTime }

    val past = items
        .asSequence()
        .filter { !it.isTaken && it.intakeTime <= now }
        .maxByOrNull { it.intakeTime }

    val candidate = future ?: past
    val sub = candidate?.let { c ->
        "${to12h(c.intakeTime)} ${c.medicationName}"
    } ?: "오늘은 없어요!"

    return WidgetSnapshot(
        subTitle = sub,
        progress = "${taken}/${total}회",
        nextRoutineId = candidate?.routineId
    )
}

internal fun to12h(t: LocalTime): String {
    val h = if (t.hour == 0 || t.hour == 12) 12 else t.hour % 12
    val ampm = if (t.hour < 12) "am" else "pm"
    val m = t.minute.toString().padStart(2, '0')
    return "$ampm $h:$m"
}