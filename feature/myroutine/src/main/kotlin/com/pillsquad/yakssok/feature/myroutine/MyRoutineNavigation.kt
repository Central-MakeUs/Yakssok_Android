package com.pillsquad.yakssok.feature.myroutine

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route

fun NavController.navigateMyRoutine(navOptions: NavOptions) {
    navigate(route = Route.MyRoutine, navOptions = navOptions)
}

fun NavGraphBuilder.myRoutineNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.MyRoutine> {

    }
}