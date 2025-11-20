package com.pillsquad.yakssok.feature.home.model

import android.util.SparseArray
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.base.UiState
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey
import kotlinx.datetime.LocalDate

data class HomeState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,

    val isInit: Boolean = true, // 앱에 처음 접속했는가?(Remind 띄우는 것 관리 용도)

    val tutorialStep: Int = -1,
    val isTutorialComplete: Boolean = false,
    val currentTargetKey: TutorialTargetKey = TutorialTargetKey.END,

    val feedbackDialogTarget: FeedbackTarget? = null,

    val selectedDate: LocalDate = LocalDate.today(),
    val selectedUserIdx: Int = 0,

    val userList: List<User> = listOf(
        User(
            id = 0,
            nickName = "나",
            profileImage = ""
        )
    ),

    val feedbackTargetList: List<FeedbackTarget> = emptyList(),
    val routineCache: SparseArray<MutableMap<LocalDate, RoutineGroup>> = SparseArray(),
    val remindList: List<Routine> = emptyList(),

    val error: Throwable? = null
) : UiState {
    val routineGroup
        get() = routineCache[selectedUserIdx]?.get(selectedDate)
}