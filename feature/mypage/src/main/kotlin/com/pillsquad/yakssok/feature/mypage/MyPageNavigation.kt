package com.pillsquad.yakssok.feature.mypage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(route = Route.MyPage, navOptions = navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateProfileEdit: () -> Unit,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
    onNavigateInfo: (String, String) -> Unit
) {
    composable<Route.MyPage> {

    }
}