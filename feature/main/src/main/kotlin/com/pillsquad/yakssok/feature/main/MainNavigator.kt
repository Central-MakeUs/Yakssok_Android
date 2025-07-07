package com.pillsquad.yakssok.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.MainTabRoute
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.home.navigation.navigateHome
import com.pillsquad.yakssok.feature.intro.navigation.navigateIntro

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination : NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Home

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun closeOptions(): NavOptions {
        return navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

//        when(tab) {
//            MainTab.HOME -> {
//                val homeNavOptions = navOptions {
//                    popUpTo(Route.Intro) { inclusive = true }
//                    launchSingleTop = true
//                }
//                navController.navigateHome(navOptions)
//            }
//        }
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackIfNotHome() {
        if(!isSameCurrentDestination<Route.Home>()) {
            popBackStack()
        }
    }

    fun navigateHome() {
        navController.navigateHome(closeOptions())
    }

    fun navigateIntro() {
        navController.navigateIntro(closeOptions())
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}