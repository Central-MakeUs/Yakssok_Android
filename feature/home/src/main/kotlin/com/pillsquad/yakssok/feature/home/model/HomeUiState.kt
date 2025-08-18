package com.pillsquad.yakssok.feature.home.model

import android.util.SparseArray
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import kotlinx.datetime.LocalDate

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val isInit: Boolean = true,
        val selectedDate: LocalDate = LocalDate.today(),
        val selectedUserIdx: Int = 0,
        val userList: List<User> = listOf(
            User(
                id = 0,
                nickName = "나",
                relationName = "나",
                profileImage = ""
            )
        ),
        val feedbackTargetList: List<FeedbackTarget> = emptyList(),
        val routineCache: SparseArray<MutableMap<LocalDate, List<Routine>>> = SparseArray(),
        val remindList: List<Routine> = emptyList()
    ) : HomeUiState
}
