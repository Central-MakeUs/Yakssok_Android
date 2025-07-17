package com.pillsquad.yakssok.feature.alert.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.alert.AlertRoute

fun NavController.navigateAlert(navOptions: NavOptions) {
    navigate(route = Route.Alert, navOptions = navOptions)
}

fun NavGraphBuilder.alertNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.Alert> {
        AlertRoute(
            onNavigateBack = onNavigateBack
        )
    }
}