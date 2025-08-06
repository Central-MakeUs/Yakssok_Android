package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import javax.inject.Inject

class PutEndRoutineUseCase @Inject constructor(
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(id: Int) = medicationRepository.putEndMedication(id.toLong())
}