package com.pillsquad.yakssok.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.MainTabRoute
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.home.navigation.navigateHome

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination : NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.HOME.route

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                // 홈 -> 검색 -> 홈 다시 눌렀을 때, 홈 탭의 스크롤 상태 등을 유지
                saveState = true
                // 목적지 자체는 제거하지 않음
//                inclusive = false
            }
            // 현재 화면이 같은 목적지면 다시 push하지 않음
            launchSingleTop = true
            // 백스택에서 제거했던 화면의 상태를 복원할지 여부
            restoreState = true
        }

        when(tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
        }
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackIfNotHome() {
        if(!isSameCurrentDestination<MainTabRoute.Home>()) {
            popBackStack()
        }
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