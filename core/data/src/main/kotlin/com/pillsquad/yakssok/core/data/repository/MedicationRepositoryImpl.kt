package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toMyRoutine
import com.pillsquad.yakssok.core.data.mapper.toPostMedicationRequest
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.MedicationRepository
import com.pillsquad.yakssok.core.model.Medication
import com.pillsquad.yakssok.core.model.MyRoutine
import com.pillsquad.yakssok.core.network.datasource.MedicationDataSource
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDataSource: MedicationDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : MedicationRepository {
    override suspend fun postMedication(medication: Medication): Result<Unit> {
        val params = medication.toPostMedicationRequest()

        val result = medicationDataSource.postMedication(params = params).toResult(
            transform = { it }
        )

        result.onSuccess {
            val currentCount = userLocalDataSource.medicationCountFlow.firstOrNull() ?: 0
            userLocalDataSource.saveMedicationCount(currentCount + 1)
        }

        return result
    }

    override suspend fun putEndMedication(medicationId: Long): Result<Unit> {
        return medicationDataSource.putEndMedication(medicationId).toResult()
    }

    override suspend fun getMyMedications(status: String?): Result<List<MyRoutine>> {
        return medicationDataSource.getMyMedications(status).toResult(
            transform = { response -> response.medicationCardResponses.map { it.toMyRoutine() } }
        )
    }
}