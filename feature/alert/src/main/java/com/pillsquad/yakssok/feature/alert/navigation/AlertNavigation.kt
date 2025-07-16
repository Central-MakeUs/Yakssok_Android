package com.pillsquad.yakssok.feature.alert.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.Route

fun NavController.navigateAlert() {
    val navOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }

    navigate(route = Route.Alert, navOptions = navOptions)
}

fun NavGraphBuilder.alertNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.Alert> {

    }
}