package com.pillsquad.yakssok.feature.calendar.model

import com.pillsquad.yakssok.core.model.Routine

data class RoutineGroup(
    val haveToTake: List<Routine>,
    val taken: List<Routine>
) {
    fun isEmpty(): Boolean = haveToTake.isEmpty() && taken.isEmpty()
}

fun List<Routine>.toRoutineGroup(): RoutineGroup {
    val (haveToTake, taken) =  this.partition { !it.isTaken }
    return RoutineGroup(haveToTake, taken)
}

fun RoutineGroup.toRoutineList(): List<Routine> = this.haveToTake + this.taken