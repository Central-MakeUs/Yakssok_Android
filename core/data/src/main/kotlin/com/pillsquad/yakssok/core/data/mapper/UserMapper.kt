package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.UserInfo
import com.pillsquad.yakssok.core.network.model.response.UserInfoResponse

internal fun UserInfoResponse.toUserInfo(): UserInfo =
    UserInfo(
        nickName = nickname,
        profileImageUrl = profileImageUrl ?: ""
    )