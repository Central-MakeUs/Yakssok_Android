package com.pillsquad.yakssok.core.model

data class FeedbackTarget(
    val userId: Int,
    val nickName: String,
    val relationName: String,
    val profileImageUrl: String,
    val feedbackType: FeedbackType,
    val routineCount: Int,
    val routineList: List<Routine>
)

enum class FeedbackType {
    NAG, PRAISE
}