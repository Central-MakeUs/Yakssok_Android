package com.pillsquad.yakssok.feature.routine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.routine.RoutineRoute

fun NavController.navigateRoutine(navOptions: NavOptions) {
    navigate(route = Route.Routine, navOptions = navOptions)
}

fun NavGraphBuilder.routineNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.Routine> {
        RoutineRoute(
            onNavigateBack = onNavigateBack
        )
    }
}