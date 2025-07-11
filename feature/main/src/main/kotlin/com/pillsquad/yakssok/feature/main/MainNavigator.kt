package com.pillsquad.yakssok.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.home.navigation.navigateHome
import com.pillsquad.yakssok.feature.intro.navigation.navigateIntro
import com.pillsquad.yakssok.feature.routine.navigation.navigateRoutine

internal class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = Route.Home

    fun closeOptions(): NavOptions {
        return navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateHome() {
        navController.navigateHome(navOptions {  })
    }

    fun navigateIntro() {
        navController.navigateIntro(closeOptions())
    }

    fun navigateRoutine() {
        navController.navigateRoutine()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}