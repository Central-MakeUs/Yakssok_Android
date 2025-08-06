package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.model.response.FeedbackTargetResponse
import com.pillsquad.yakssok.core.network.model.response.FollowerInfoResponse
import com.pillsquad.yakssok.core.network.model.response.FollowingInfoResponse

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

internal fun FeedbackTargetResponse.toFeedBackTarget(): FeedbackTarget =
    FeedbackTarget(
        userId = userId,
        notTakenCount = notTakenCount
    )
