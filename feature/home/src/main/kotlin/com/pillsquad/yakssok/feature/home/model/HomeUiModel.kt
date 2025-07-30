package com.pillsquad.yakssok.feature.home.model

import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.User
import kotlinx.datetime.LocalDate

data class HomeUiModel(
    val selectedUserIdx: Int = 0,
    val selectedDate: LocalDate = LocalDate.today(),
    val userList: List<User> = listOf(
        User(
            id = 0,
            nickName = "나",
            relationName = "나",
            profileImage = "",
            medicineCache = mutableMapOf()
        )
    ),
    val showFeedBackSection: Boolean = false,
)