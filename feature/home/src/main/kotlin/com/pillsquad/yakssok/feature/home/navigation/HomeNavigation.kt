package com.pillsquad.yakssok.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.MainTabRoute
import com.pillsquad.yakssok.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
) {
    composable<MainTabRoute.Home> {
        HomeRoute()
    }
}