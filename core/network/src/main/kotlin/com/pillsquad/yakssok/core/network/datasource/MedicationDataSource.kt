package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest
import com.pillsquad.yakssok.core.network.model.response.MedicationCardResponse
import com.pillsquad.yakssok.core.network.model.response.MyMedicationListResponse

interface MedicationDataSource {
    suspend fun putEndMedication(medicationId: Long): ApiResponse<Unit>
    suspend fun getMyMedications(status: String?): ApiResponse<MyMedicationListResponse>
    suspend fun postMedication(params: PostMedicationRequest): ApiResponse<Unit>
}