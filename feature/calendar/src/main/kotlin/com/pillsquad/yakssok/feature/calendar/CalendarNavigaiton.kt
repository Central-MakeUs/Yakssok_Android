package com.pillsquad.yakssok.feature.calendar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route

fun NavController.navigateCalendar(navOptions: NavOptions) {
    navigate(route = Route.Calendar, navOptions = navOptions)
}

fun NavGraphBuilder.calendarNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateRoutine: (String) -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateMyPage: () -> Unit,
) {
    composable<Route.Calendar> {

    }
}