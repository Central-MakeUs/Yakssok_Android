package com.pillsquad.yakssok.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
//    @Serializable
//    data object Setting : Route
//
//    @Serializable
//    data class Detail(val id: String) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute
}