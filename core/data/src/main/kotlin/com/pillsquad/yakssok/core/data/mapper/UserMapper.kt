package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.model.response.FollowingInfoResponse
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse

internal fun MyInfoResponse.toMyInfo(): MyInfo =
    MyInfo(
        nickName = nickName,
        profileImage = profileImage ?: "",
        medicationCount = medicationCount,
        followingCount = followingCount
    )

internal fun FollowingInfoResponse.toUser(): User =
    User(
        id = userId,
        nickName = nickName,
        relationName = relationName,
        profileImage = profileImageUrl ?: ""
    )