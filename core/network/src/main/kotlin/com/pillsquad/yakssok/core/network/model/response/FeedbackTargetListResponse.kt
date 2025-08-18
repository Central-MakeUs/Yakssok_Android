package com.pillsquad.yakssok.core.network.model.response

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FeedbackTargetListResponse(
    @SerialName("followingMedicationStatusResponses")
    val feedbackTargetResponseList: List<FeedbackTargetResponse>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FeedbackTargetResponse(
    val userId: Int,
    val nickName: String,
    val relationName: String,
    val profileImageUrl: String?,
    val feedbackType: String,
    val medicationCount: Int,
    val medicationDetails: List<MedicationDetail>
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MedicationDetail(
    val type: String,
    val name: String,
    val time: String
)