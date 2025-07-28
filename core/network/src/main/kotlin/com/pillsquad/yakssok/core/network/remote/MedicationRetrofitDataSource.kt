package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.MedicationDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest
import com.pillsquad.yakssok.core.network.model.response.MedicationCardResponse
import com.pillsquad.yakssok.core.network.service.MedicationApi
import javax.inject.Inject

class MedicationRetrofitDataSource @Inject constructor(
    private val medicationApi: MedicationApi
) : MedicationDataSource {
    override suspend fun putEndMedication(medicationId: Long): ApiResponse<Unit> =
        medicationApi.putEndMedication(medicationId)

    override suspend fun getMyMedications(status: String): ApiResponse<List<MedicationCardResponse>> =
        medicationApi.getMyMedications(status)

    override suspend fun postMedication(params: PostMedicationRequest): ApiResponse<Unit> =
        medicationApi.postMedication(params)
}