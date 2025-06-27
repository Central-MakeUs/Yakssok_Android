package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.datasource_api.model.UserData

internal fun UserData.toUser(): User =
    User(
        id = id,
        name = name
    )