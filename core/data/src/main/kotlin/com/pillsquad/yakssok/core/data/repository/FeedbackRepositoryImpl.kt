package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.FeedbackRepository
import com.pillsquad.yakssok.core.network.datasource.FeedbackDataSource
import com.pillsquad.yakssok.core.network.model.request.FeedbackRequest
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val feedbackDataSource: FeedbackDataSource
): FeedbackRepository {
    override suspend fun postFeedback(
        userId: Int,
        message: String,
        type: String
    ): Result<Unit> {
        val params = FeedbackRequest(userId, message, type)
        return feedbackDataSource.postFeedback(params).toResult()
    }
}