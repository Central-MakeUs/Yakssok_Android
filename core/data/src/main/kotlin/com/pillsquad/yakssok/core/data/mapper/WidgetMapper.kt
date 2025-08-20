package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.WidgetSnapshot
import com.pillsquad.yakssok.datastore.model.WidgetEntity

internal fun WidgetEntity.toWidgetSnapShot(): WidgetSnapshot =
    WidgetSnapshot(
        subTitle = sub,
        progress = progress,
        nextRoutineId = nextRoutine
    )

internal fun WidgetSnapshot.toWidgetEntity(): WidgetEntity =
    WidgetEntity(
        sub = subTitle,
        progress = progress,
        nextRoutine = nextRoutineId
    )