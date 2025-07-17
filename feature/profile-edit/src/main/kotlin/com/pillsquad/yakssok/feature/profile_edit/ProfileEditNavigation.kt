package com.pillsquad.yakssok.feature.profile_edit

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pillsquad.yakssok.core.navigation.Route

fun NavController.navigateProfileEdit(navOptions: NavOptions) {
    navigate(route = Route.ProfileEdit, navOptions = navOptions)
}

fun NavGraphBuilder.profileEditNavGraph(
    onNavigateBack: () -> Unit
) {
    composable<Route.ProfileEdit> {

    }
}