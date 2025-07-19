package com.pillsquad.yakssok.feature.myroutine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.myroutine.MyRoutineRoute

fun NavController.navigateMyRoutine(navOptions: NavOptions) {
    navigate(route = Route.MyRoutine, navOptions = navOptions)
}

fun NavGraphBuilder.myRoutineNavGraph(
    onNavigateRoutine: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    composable<Route.MyRoutine> {
        MyRoutineRoute(
            onNavigateRoutine = onNavigateRoutine,
            onNavigateBack = onNavigateBack
        )
    }
}