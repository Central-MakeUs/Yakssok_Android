package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.Medication

interface MedicationRepository {
    suspend fun postMedication(medication: Medication): Result<Unit>
}