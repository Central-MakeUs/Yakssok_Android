package com.pillsquad.yakssok.feature.profile_edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.profile_edit.ProfileEditRoute

fun NavController.navigateProfileEdit(navOptions: NavOptions) {
    navigate(route = Route.ProfileEdit, navOptions = navOptions)
}

fun NavGraphBuilder.profileEditNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.ProfileEdit> {
        ProfileEditRoute(
            onNavigateBack = onNavigateBack
        )
    }
}