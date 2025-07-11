package com.pillsquad.yakssok.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Intro : Route

    @Serializable
    data object Home : Route

    @Serializable
    data class Routine(val name: String) : Route
}