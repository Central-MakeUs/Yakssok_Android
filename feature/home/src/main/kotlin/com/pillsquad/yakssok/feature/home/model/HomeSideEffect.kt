package com.pillsquad.yakssok.feature.home.model

import com.pillsquad.yakssok.core.ui.base.SideEffect

sealed interface HomeSideEffect : SideEffect {
    data class ShowError(val throwable: Throwable) : HomeSideEffect
    data object NavigateToMate : HomeSideEffect
    data object NavigateToRoutine : HomeSideEffect
    data object NavigateToCalendar : HomeSideEffect
    data object NavigateToAlert : HomeSideEffect
    data object NavigateToMyPage : HomeSideEffect
}