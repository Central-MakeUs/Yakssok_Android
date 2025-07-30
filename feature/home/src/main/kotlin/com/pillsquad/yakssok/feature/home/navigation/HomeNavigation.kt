package com.pillsquad.yakssok.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(route = Route.Home, navOptions = navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    onNavigateRoutine: () -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateMyPage: () -> Unit,
    onNavigateCalendar: () -> Unit
) {
    composable<Route.Home> {
        HomeRoute(
            onNavigateRoutine = onNavigateRoutine,
            onNavigateAlert = onNavigateAlert,
            onNavigateMate = onNavigateMate,
            onNavigateMyPage = onNavigateMyPage,
            onNavigateCalendar = onNavigateCalendar
        )
    }
}