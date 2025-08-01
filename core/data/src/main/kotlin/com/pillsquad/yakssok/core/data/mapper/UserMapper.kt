package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.UserInfo
import com.pillsquad.yakssok.core.network.model.response.MyInfoResponse
import com.pillsquad.yakssok.core.network.model.response.UserInfoResponse

internal fun MyInfoResponse.toMyInfo(): MyInfo =
    MyInfo(
        nickName = nickName,
        profileImage = profileImage ?: "",
        medicationCount = medicationCount,
        followingCount = followingCount
    )

internal fun UserInfoResponse.toUserInfo(): UserInfo =
    UserInfo(
        nickName = nickname,
        profileImageUrl = profileImageUrl ?: ""
    )