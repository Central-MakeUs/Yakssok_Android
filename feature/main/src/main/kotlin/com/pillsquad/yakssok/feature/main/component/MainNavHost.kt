package com.pillsquad.yakssok.feature.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.pillsquad.yakssok.feature.alert.navigation.alertNavGraph
import com.pillsquad.yakssok.feature.home.navigation.homeNavGraph
import com.pillsquad.yakssok.feature.intro.navigation.introNavGraph
import com.pillsquad.yakssok.feature.main.MainNavigator
import com.pillsquad.yakssok.feature.mate.navigation.mateNavGraph
import com.pillsquad.yakssok.feature.routine.navigation.routineNavGraph

@Composable
internal fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
        ) {
            homeNavGraph(
                onNavigateRoutine = navigator::navigateRoutine,
                onNavigateAlert = navigator::navigateAlert,
                onNavigateMate = navigator::navigateMate
            )

            introNavGraph(onNavigateHome = navigator::navigateHome)

            routineNavGraph(onNavigateBack = navigator::popBackStack)

            alertNavGraph(onNavigateBack = navigator::popBackStack)

            mateNavGraph(onNavigateBack = navigator::popBackStack)
        }
    }
}