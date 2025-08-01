package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.FeedbackDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.FeedbackRequest
import com.pillsquad.yakssok.core.network.service.FeedbackApi
import javax.inject.Inject

class FeedbackRetrofitDataSource @Inject constructor(
    private val feedbackApi: FeedbackApi
) : FeedbackDataSource {
    override suspend fun postFeedback(params: FeedbackRequest): ApiResponse<Unit> =
        feedbackApi.postFeedback(params)
}