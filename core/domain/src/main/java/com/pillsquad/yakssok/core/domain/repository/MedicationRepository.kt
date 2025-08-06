package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.Medication
import com.pillsquad.yakssok.core.model.MyRoutine

interface MedicationRepository {
    suspend fun postMedication(medication: Medication): Result<Unit>
    suspend fun putEndMedication(medicationId: Long): Result<Unit>
    suspend fun getMyMedications(status: String?): Result<List<MyRoutine>>
}