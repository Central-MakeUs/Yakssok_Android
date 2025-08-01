package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.FeedbackRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {

    @POST("/api/feedbacks")
    suspend fun postFeedback(
        @Body params: FeedbackRequest
    ): ApiResponse<Unit>
}