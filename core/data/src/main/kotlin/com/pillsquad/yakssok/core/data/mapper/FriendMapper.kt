package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.FeedbackType
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.model.response.FeedbackTargetResponse
import com.pillsquad.yakssok.core.network.model.response.FollowerInfoResponse
import com.pillsquad.yakssok.core.network.model.response.FollowingInfoResponse
import com.pillsquad.yakssok.core.network.model.response.MedicationDetail
import kotlinx.datetime.LocalTime

internal fun FollowingInfoResponse.toUser(): User =
    User(
        id = userId,
        nickName = nickName,
        relationName = relationName,
        profileImage = profileImageUrl ?: ""
    )

internal fun FollowingInfoResponse.toFollowUser(): User =
    User(
        id = userId,
        nickName = nickName,
        relationName = nickName,
        profileImage = profileImageUrl ?: ""
    )

internal fun FollowerInfoResponse.toUser(): User =
    User(
        id = userId,
        nickName = nickName,
        relationName = nickName,
        profileImage = profileImageUrl ?: ""
    )

internal fun FeedbackTargetResponse.toFeedBackTarget(): FeedbackTarget {
    val type = feedbackType.toFeedbackType()

    return FeedbackTarget(
        userId = userId,
        nickName = nickName,
        relationName = relationName,
        profileImageUrl = profileImageUrl ?: "",
        feedbackType = type,
        routineCount = medicationCount,
        routineList = medicationDetails.map { it.toRoutine(type == FeedbackType.PRAISE) }
    )
}

internal fun MedicationDetail.toRoutine(isTaken: Boolean): Routine =
    Routine(
        routineId = null,
        medicationName = name,
        medicationType = type.toMedicationType(),
        intakeTime = LocalTime.parse(time),
        isTaken = isTaken
    )

internal fun String.toFeedbackType(): FeedbackType =
    try {
        FeedbackType.valueOf(this)
    } catch (e: Exception) {
        e.printStackTrace()
        FeedbackType.NAG
    }