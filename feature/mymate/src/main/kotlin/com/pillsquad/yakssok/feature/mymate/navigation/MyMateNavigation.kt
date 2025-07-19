package com.pillsquad.yakssok.feature.mymate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.mymate.MyMateRoute

fun NavController.navigateMyMate(navOptions: NavOptions) {
    navigate(route = Route.MyMate, navOptions = navOptions)
}

fun NavGraphBuilder.myMateNavGraph(
    onNavigateMate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    composable<Route.MyMate> {
        MyMateRoute(
            onNavigateMate = onNavigateMate,
            onNavigateBack = onNavigateBack
        )
    }
}