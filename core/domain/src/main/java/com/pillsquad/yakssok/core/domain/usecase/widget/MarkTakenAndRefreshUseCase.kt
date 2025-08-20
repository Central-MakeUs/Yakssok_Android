package com.pillsquad.yakssok.core.domain.usecase.widget

import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.domain.repository.WidgetRepository
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class MarkTakenAndRefreshUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(scheduleId: Int, today: LocalDate = LocalDate.today()): Result<WidgetSnapshot> =
        routineRepository.putTakeRoutine(scheduleId).mapCatching {
            val updated = routineRepository.getMyRoutine(today, today).getOrThrow()
            val snap = computeSnapshot(LocalTime.now(), updated, today)
            widgetRepository.save(snap).getOrThrow()
            snap
        }
}