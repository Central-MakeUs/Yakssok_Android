package com.pillsquad.yakssok.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.pillsquad.yakssok.core.navigation.Route
import com.pillsquad.yakssok.feature.alert.navigation.navigateAlert
import com.pillsquad.yakssok.feature.calendar.navigation.navigateCalendar
import com.pillsquad.yakssok.feature.home.navigation.navigateHome
import com.pillsquad.yakssok.feature.info.navigateInfo
import com.pillsquad.yakssok.feature.intro.navigation.navigateIntro
import com.pillsquad.yakssok.feature.mate.navigation.navigateMate
import com.pillsquad.yakssok.feature.mymate.navigation.navigateMyMate
import com.pillsquad.yakssok.feature.mypage.navigation.navigateMyPage
import com.pillsquad.yakssok.feature.myroutine.navigation.navigateMyRoutine
import com.pillsquad.yakssok.feature.profile_edit.navigation.navigateProfileEdit
import com.pillsquad.yakssok.feature.routine.navigation.navigateRoutine

internal class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = Route.Intro

    fun closeOptions(): NavOptions {
        return navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    val defaultNavOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }

    fun popBackStack() {
        navController.previousBackStackEntry?.let {
            navController.popBackStack()
        }
    }

    fun navigateHome() {
        navController.navigateHome(closeOptions())
    }

    fun navigateIntro() {
        navController.navigateIntro(closeOptions())
    }

    fun navigateRoutine() {
        navController.navigateRoutine(defaultNavOptions)
    }

    fun navigateAlert() {
        navController.navigateAlert(defaultNavOptions)
    }

    fun navigateMate() {
        navController.navigateMate(defaultNavOptions)
    }

    fun navigateInfo(title: String, url: String) {
        navController.navigateInfo(title = title, url = url, navOptions = defaultNavOptions)
    }

    fun navigateMyMate() {
        navController.navigateMyMate(defaultNavOptions)
    }

    fun navigateMyPage() {
        navController.navigateMyPage(defaultNavOptions)
    }

    fun navigateMyRoutine() {
        navController.navigateMyRoutine(defaultNavOptions)
    }

    fun navigateProfileEdit() {
        navController.navigateProfileEdit(defaultNavOptions)
    }

    fun navigateCalendar() {
        navController.navigateCalendar(defaultNavOptions)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}