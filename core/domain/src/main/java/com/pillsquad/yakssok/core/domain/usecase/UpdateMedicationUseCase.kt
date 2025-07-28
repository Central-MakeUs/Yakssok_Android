package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import com.pillsquad.yakssok.core.model.Medication
import javax.inject.Inject

class PostMedicationUseCase @Inject constructor(
    private val medicationRepository: MedicationRepository
) {
    suspend operator fun invoke(medication: Medication): Result<Unit> {
        return medicationRepository.postMedication(medication)
    }
}