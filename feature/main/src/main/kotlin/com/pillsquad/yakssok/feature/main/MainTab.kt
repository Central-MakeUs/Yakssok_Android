package com.pillsquad.yakssok.feature.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.pillsquad.yakssok.core.navigation.MainTabRoute
import com.pillsquad.yakssok.core.navigation.Route

internal enum class MainTab(
    @param:DrawableRes
    val iconResId: Int,
    internal val contentDescription: String,
    val route: MainTabRoute
) {

    ;
//    HOME(
//        iconResId = R.drawable.ic_home,
//        contentDescription = "Home",
//        route = MainTabRoute.Home,
//    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }
        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}