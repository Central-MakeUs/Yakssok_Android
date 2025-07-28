package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest
import com.pillsquad.yakssok.core.network.model.response.MedicationCardResponse

interface MedicationDataSource {
    suspend fun putEndMedication(medicationId: Long): ApiResponse<Unit>
    suspend fun getMyMedications(status: String): ApiResponse<List<MedicationCardResponse>>
    suspend fun postMedication(params: PostMedicationRequest): ApiResponse<Unit>
}