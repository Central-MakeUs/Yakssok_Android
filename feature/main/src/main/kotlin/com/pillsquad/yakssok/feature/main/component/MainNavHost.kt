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
import com.pillsquad.yakssok.feature.calendar.navigation.calendarNavGraph
import com.pillsquad.yakssok.feature.home.navigation.homeNavGraph
import com.pillsquad.yakssok.feature.info.infoNavGraph
import com.pillsquad.yakssok.feature.intro.navigation.introNavGraph
import com.pillsquad.yakssok.feature.main.MainNavigator
import com.pillsquad.yakssok.feature.mate.navigation.mateNavGraph
import com.pillsquad.yakssok.feature.mymate.navigation.myMateNavGraph
import com.pillsquad.yakssok.feature.mypage.navigation.myPageNavGraph
import com.pillsquad.yakssok.feature.myroutine.navigation.myRoutineNavGraph
import com.pillsquad.yakssok.feature.profile_edit.navigation.profileEditNavGraph
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
                onNavigateMate = navigator::navigateMate,
                onNavigateMyPage = navigator::navigateMyPage,
                onNavigateCalendar = navigator::navigateCalendar
            )

            introNavGraph(onNavigateHome = navigator::navigateHome)

            routineNavGraph(onNavigateBack = navigator::popBackStack)

            alertNavGraph(onNavigateBack = navigator::popBackStack)

            mateNavGraph(onNavigateBack = navigator::popBackStack)

            infoNavGraph(onNavigateBack = navigator::popBackStack)

            myMateNavGraph(
                onNavigateMate = navigator::navigateMate,
                onNavigateBack = navigator::popBackStack
            )

            myPageNavGraph(
                onNavigateBack = navigator::popBackStack,
                onNavigateProfileEdit = navigator::navigateProfileEdit,
                onNavigateMyRoutine = navigator::navigateMyRoutine,
                onNavigateMyMate = navigator::navigateMyMate,
                onNavigateInfo = navigator::navigateInfo,
                onNavigateIntro = navigator::navigateIntro
            )

            myRoutineNavGraph(
                onNavigateRoutine = navigator::navigateRoutine,
                onNavigateBack = navigator::popBackStack
            )

            profileEditNavGraph(onNavigateBack = navigator::popBackStack)

            calendarNavGraph(
                onNavigateBack = navigator::popBackStack,
                onNavigateRoutine = navigator::navigateRoutine,
                onNavigateAlert = navigator::navigateAlert,
                onNavigateMate = navigator::navigateMate,
                onNavigateMyPage = navigator::navigateMyPage
            )
        }
    }
}