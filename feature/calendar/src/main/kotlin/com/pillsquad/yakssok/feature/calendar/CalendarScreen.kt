package com.pillsquad.yakssok.feature.calendar

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.MateLazyRow

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateRoutine: (String) -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateMyPage: () -> Unit,
) {
    val scrollState = rememberScrollState()

    CalendarScreen(
        onNavigateBack = onNavigateBack,
        onNavigateRoutine = { onNavigateRoutine("김OO") },
        onNavigateAlert = onNavigateAlert,
        onNavigateMate = onNavigateMate,
        onNavigateMyPage = onNavigateMyPage
    )
}

@Composable
internal fun CalendarScreen(
    scrollState: ScrollState = rememberScrollState(),
    onNavigateBack: () -> Unit = {},
    onNavigateRoutine: () -> Unit = {},
    onNavigateAlert: () -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateMyPage: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey50)
            .systemBarsPadding()
    ) {
        YakssokTopAppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            onBackClick = onNavigateBack,
            onNavigateAlert = onNavigateAlert,
            onNavigateMy = onNavigateMyPage
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(YakssokTheme.color.grey100)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}