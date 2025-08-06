package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import com.pillsquad.yakssok.core.model.MyRoutine
import javax.inject.Inject

class GetMyRoutineListUseCase @Inject constructor(
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(): Result<List<MyRoutine>> =
        medicationRepository.getMyMedications(null)
}