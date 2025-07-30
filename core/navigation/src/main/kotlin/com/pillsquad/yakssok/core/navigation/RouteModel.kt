package com.pillsquad.yakssok.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Intro : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Routine : Route

    @Serializable
    data object Alert : Route

    @Serializable
    data object Mate: Route

    @Serializable
    data object MyPage: Route

    @Serializable
    data object ProfileEdit: Route

    @Serializable
    data object MyRoutine: Route

    @Serializable
    data object MyMate: Route

    @Serializable
    data class Info(val title: String, val url: String): Route

    @Serializable
    data object Calendar: Route
}