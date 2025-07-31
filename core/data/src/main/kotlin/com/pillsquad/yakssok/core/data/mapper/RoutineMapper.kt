package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.network.model.response.RoutineResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

internal fun RoutineResponse.toRoutineAndTakenCache(
    userId: Int = 0
): UserCache {
    val routineMap = mutableMapOf<LocalDate, List<Routine>>()
    val takenMap = mutableMapOf<LocalDate, Boolean>()

    groupedSchedules.forEach { (dateStr, scheduleGroups) ->
        val date = LocalDate.parse(dateStr)
        val routines = scheduleGroups.flatMap { group ->
            group.schedules.map { dto ->
                Routine(
                    routineId = dto.scheduleId,
                    medicationName = dto.medicationName,
                    medicationType = dto.medicationType.toMedicationType(),
                    intakeTime = LocalTime.parse(dto.intakeTime),
                    isTaken = dto.isTaken
                )
            }
        }
        routineMap[date] = routines
        takenMap[date] = scheduleGroups.all { it.allTaken }
    }

    return UserCache(
        userId = userId,
        routineCache = routineMap,
        takenCache = takenMap
    )
}
