package com.pillsquad.yakssok.feature.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.mypage.MyPageRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(route = Route.MyPage, navOptions = navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateProfileEdit: () -> Unit,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
    onNavigateInfo: (String, String) -> Unit,
    onNavigateIntro: () -> Unit,
) {
    composable<Route.MyPage> {
        MyPageRoute(
            onNavigateBack = onNavigateBack,
            onNavigateProfileEdit = onNavigateProfileEdit,
            onNavigateMyRoutine = onNavigateMyRoutine,
            onNavigateMyMate = onNavigateMyMate,
            onNavigateInfo = onNavigateInfo,
            onNavigateIntro = onNavigateIntro
        )
    }
}