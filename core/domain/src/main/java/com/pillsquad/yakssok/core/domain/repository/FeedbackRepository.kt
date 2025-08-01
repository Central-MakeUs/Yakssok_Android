package com.pillsquad.yakssok.core.domain.repository

interface FeedbackRepository {
    suspend fun postFeedback(userId: Int, message: String, type: String): Result<Unit>
}