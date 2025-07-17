package com.pillsquad.yakssok.feature.info

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pillsquad.yakssok.core.navigation.Route

fun NavController.navigateInfo(title: String, url: String, navOptions: NavOptions) {
    navigate(route = Route.Info(title, url), navOptions = navOptions)
}

fun NavGraphBuilder.infoNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.Info> {
//        InfoRoute(
//            title = it.toRoute<Route.Info>().title,
//            url = it.toRoute<Route.Info>().url,
//            onNavigateBack = onNavigateBack
//        )
    }
}