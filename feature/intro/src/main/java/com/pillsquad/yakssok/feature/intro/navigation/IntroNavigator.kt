package com.pillsquad.yakssok.feature.intro.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.intro.IntroRoute

fun NavController.navigateIntro() {
    val navOptions = navOptions {
        popUpTo(graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }

    navigate(route = Route.Intro, navOptions = navOptions)
}

fun NavGraphBuilder.introNavGraph(
) {
    composable<Route.Intro> {
        IntroRoute()
    }
}