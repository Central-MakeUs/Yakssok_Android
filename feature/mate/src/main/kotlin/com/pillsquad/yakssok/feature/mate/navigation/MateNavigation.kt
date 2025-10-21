package com.pillsquad.yakssok.feature.mate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.mate.MateRoute

fun NavController.navigateMate(code: String, navOptions: NavOptions) {
    navigate(route = Route.Mate(code), navOptions = navOptions)
}

fun NavGraphBuilder.mateNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.Mate> {
        MateRoute(
            code = it.toRoute<Route.Mate>().code,
            onNavigateBack = onNavigateBack
        )
    }
}