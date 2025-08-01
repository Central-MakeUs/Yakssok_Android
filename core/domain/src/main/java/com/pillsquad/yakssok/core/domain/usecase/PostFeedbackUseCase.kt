package com.pillsquad.yakssok.core.domain.usecase

import android.util.Log
import com.pillsquad.yakssok.core.domain.repository.FeedbackRepository
import javax.inject.Inject

class PostFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(userId: Int, message: String, type: String) {
        feedbackRepository.postFeedback(userId, message, type)
            .onSuccess {
                Log.d("PostFeedbackUseCase", "Feedback posted successfully")
            }.onFailure {
                it.printStackTrace()
                Log.e("PostFeedbackUseCase", "Failed to post feedback", it)
            }
    }
}