package com.pillsquad.yakssok.feature.calendar.model

import android.util.SparseArray
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.UserCache
import kotlinx.datetime.LocalDate

class CalendarCacheManager(
    private val routineCache: SparseArray<MutableMap<LocalDate, RoutineGroup>>,
    private val takenCache: SparseArray<MutableMap<LocalDate, Boolean>>
) {

    fun merge(userIdx: Int, cache: UserCache) {
        val currentRoutine = routineCache[userIdx] ?: mutableMapOf()
        val newRoutine = currentRoutine.toMutableMap().apply {
            cache.routineCache.forEach { (date, list) -> this[date] = list.toRoutineGroup() }
        }
        routineCache.put(userIdx, newRoutine)

        val currentTaken = takenCache[userIdx] ?: mutableMapOf()
        val newTaken = currentTaken.toMutableMap().apply {
            cache.takenCache.forEach { (date, taken) -> this[date] = taken }
        }
        takenCache.put(userIdx, newTaken)
    }

    fun updateRoutine(userIdx: Int, date: LocalDate, routines: List<Routine>) {
        val routineMap = routineCache[userIdx] ?: mutableMapOf()
        routineMap[date] = routines.toRoutineGroup()
        routineCache.put(userIdx, routineMap)

        updateTaken(userIdx, date)
    }

    fun updateTaken(userIdx: Int, date: LocalDate) {
        val routineMap = routineCache[userIdx] ?: return
        val routineGroup = routineMap[date] ?: return
        val allTaken = !routineGroup.isEmpty() && routineGroup.haveToTake.isEmpty()

        val takenMap = takenCache[userIdx] ?: mutableMapOf()
        takenMap[date] = allTaken
        takenCache.put(userIdx, takenMap)
    }
}