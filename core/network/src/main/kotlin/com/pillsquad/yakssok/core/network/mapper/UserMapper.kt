package com.pillsquad.yakssok.core.network.mapper

import com.pillsquad.yakssok.core.network.model.response.UserResponse
import com.pillsquad.yakssok.datasource_api.model.UserData

internal fun UserResponse.toUserData(): UserData =
    UserData(
        id = id,
        name = name
    )
