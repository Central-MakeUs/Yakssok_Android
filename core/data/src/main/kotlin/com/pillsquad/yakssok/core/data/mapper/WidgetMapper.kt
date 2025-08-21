package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.WidgetItem
import com.pillsquad.yakssok.core.model.WidgetSnapshot
import com.pillsquad.yakssok.datastore.model.WidgetEntity
import com.pillsquad.yakssok.datastore.model.WidgetRow

internal fun WidgetEntity.toWidgetSnapShot(): WidgetSnapshot =
    WidgetSnapshot(
        rows = rows.map { it.toWidgetItem() },
        progress = progress,
    )

internal fun WidgetRow.toWidgetItem(): WidgetItem =
    WidgetItem(
        routineId = routineId,
        intakeTime = intakeTime,
        medicationName = medicationName,
        isTaken = isTaken
    )

internal fun WidgetSnapshot.toWidgetEntity(): WidgetEntity =
    WidgetEntity(
        rows = rows.map { it.toWidgetRow() },
        progress = progress,
    )

internal fun WidgetItem.toWidgetRow(): WidgetRow =
    WidgetRow(
        routineId = routineId,
        intakeTime = intakeTime,
        medicationName = medicationName,
        isTaken = isTaken
    )