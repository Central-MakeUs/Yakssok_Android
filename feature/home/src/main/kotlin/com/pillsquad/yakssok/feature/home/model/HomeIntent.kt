package com.pillsquad.yakssok.feature.home.model

import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.ui.base.UiIntent
import kotlinx.datetime.LocalDate

sealed interface HomeIntent : UiIntent {
    data object LoadInitial : HomeIntent
    data object Refresh : HomeIntent

    data object ClearRemind : HomeIntent
    data object CloseFBDialog : HomeIntent // FeedbackDialog 닫기(FeedbackTarget null 변환)
    data object NextTutorialStep : HomeIntent
    data object NavigateToMate : HomeIntent
    data object NavigateToCalendar : HomeIntent
    data object NavigateToAlert : HomeIntent
    data object NavigateToMyPage : HomeIntent
    data object NavigateToRoutine : HomeIntent

    data class SelectDate(val date: LocalDate) : HomeIntent
    data class SelectUser(val idx: Int) : HomeIntent
    data class ToggleRoutine(val routineId: Int) : HomeIntent
    data class PostFeedback(
        val userId: Int,
        val message: String,
        val type: String
    ) : HomeIntent
    data class SetFeedbackDialog(
        val feedbackTarget: FeedbackTarget?
    ) : HomeIntent
}