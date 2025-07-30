package com.pillsquad.yakssok.feature.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.calendar.CalendarRoute

fun NavController.navigateCalendar(navOptions: NavOptions) {
    navigate(route = Route.Calendar, navOptions = navOptions)
}

fun NavGraphBuilder.calendarNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateRoutine: () -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateMyPage: () -> Unit,
) {
    composable<Route.Calendar> {
        CalendarRoute(
            onNavigateBack = onNavigateBack,
            onNavigateRoutine = onNavigateRoutine,
            onNavigateAlert = onNavigateAlert,
            onNavigateMate = onNavigateMate,
            onNavigateMyPage = onNavigateMyPage
        )
    }
}