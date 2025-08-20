package com.pillsquad.yakssok.core.domain.usecase.widget

import com.pillsquad.yakssok.core.domain.repository.WidgetRepository
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWidgetSnapshotUseCase @Inject constructor(
    private val widgetRepository: WidgetRepository
) {
    operator fun invoke(): Flow<WidgetSnapshot> = widgetRepository.snapshotFlow()
}