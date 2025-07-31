package com.pillsquad.yakssok.core.domain.usecase

import android.util.Log
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import javax.inject.Inject

class UpdateRoutineTakenUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(scheduleId: Int) {
        routineRepository.putTakeRoutine(scheduleId = scheduleId)
            .onFailure {
                it.printStackTrace()
                Log.e("UpdateRoutineTakenUseCase", "invoke: $it")
            }
    }
}