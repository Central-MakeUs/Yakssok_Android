package com.pillsquad.yakssok.feature.mate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.mate.MateRoute

fun NavController.navigateMate() {
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }

    navigate(route = Route.Mate, navOptions = navOptions)
}

fun NavGraphBuilder.mateNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.Mate> {
        MateRoute(
            onNavigateBack = onNavigateBack
        )
    }
}