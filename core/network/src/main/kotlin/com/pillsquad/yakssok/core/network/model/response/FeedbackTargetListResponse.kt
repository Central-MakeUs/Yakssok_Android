package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FeedbackTargetListResponse(
    @SerialName("followingMedicationStatusResponses")
    val feedbackTargetList: List<FeedbackTarget>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FeedbackTarget(
    val userId: Int,
    val nickName: String,
    val relationName: String,
    val profileImageUrl: String?,
    val notTakenCount: Int
)