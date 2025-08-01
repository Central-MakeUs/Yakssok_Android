package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.FeedbackRequest

interface FeedbackDataSource {

    suspend fun postFeedback(params: FeedbackRequest): ApiResponse<Unit>
}