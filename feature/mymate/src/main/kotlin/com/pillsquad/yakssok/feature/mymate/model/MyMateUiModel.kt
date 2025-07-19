package com.pillsquad.yakssok.feature.mymate.model

import com.pillsquad.yakssok.core.model.Mate

data class MyMateUiModel(
    val followingList: List<Mate> = emptyList(),
    val followerList: List<Mate> = emptyList(),
)
