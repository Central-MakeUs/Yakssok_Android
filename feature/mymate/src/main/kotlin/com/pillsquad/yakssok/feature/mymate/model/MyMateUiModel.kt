package com.pillsquad.yakssok.feature.mymate.model

import com.pillsquad.yakssok.core.model.User

data class MyMateUiModel(
    val followingList: List<User> = emptyList(),
    val followerList: List<User> = emptyList(),
)
