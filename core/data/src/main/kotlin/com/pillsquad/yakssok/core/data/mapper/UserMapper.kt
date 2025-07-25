package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import com.pillsquad.yakssok.core.network.model.response.UserResponse

internal fun UserResponse.toUser(): User =
    User(
        id = id,
        name = name
    )

internal fun MyInfoResponse.toMyInfo(): MyInfo =
    MyInfo(
        nickName = nickName,
        profileImage = profileImage,
        medicationCount = medicationCount,
        followingCount = followingCount
    )