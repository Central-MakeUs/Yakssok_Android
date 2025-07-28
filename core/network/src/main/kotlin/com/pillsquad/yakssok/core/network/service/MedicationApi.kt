package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.PostMedicationRequest
import com.pillsquad.yakssok.core.network.model.response.MedicationCardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicationApi {

    @PUT("/api/medications/{medicationId}/end")
    suspend fun putEndMedication(
        @Path("medicationId") medicationId: Long
    ): ApiResponse<Unit>

    @GET("/api/medications")
    suspend fun getMyMedications(
        @Query("status") status: String
    ): ApiResponse<List<MedicationCardResponse>>

    @POST("/api/medications")
    suspend fun postMedication(
        @Body params: PostMedicationRequest
    ): ApiResponse<Unit>
}