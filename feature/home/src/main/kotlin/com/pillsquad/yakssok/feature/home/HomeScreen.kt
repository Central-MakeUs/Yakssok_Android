package com.pillsquad.yakssok.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Mate
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.home.component.WeekDataSelector
import java.time.LocalDate

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf("") }


    HomeScreen(

    )
}

@Composable
private fun HomeScreen(
    isRounded: Boolean = false,
    onClickMate: (Mate) -> Unit = {},
    onNavigateMy: () -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateAlarm: () -> Unit = {},
    onNavigateRoutine: () -> Unit = {},
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey50)
    ) {
        YakssokTopAppBar(
            isLogo = true,
            onNavigateAlarm = onNavigateAlarm,
            onNavigateMy = onNavigateMy
        )
        HomeContent(
            modifier = Modifier.weight(1f),
            isRounded = isRounded,
            onNavigateMate = onNavigateMate,
            onClickMate = onClickMate
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    mateList: List<Mate> = emptyList(),
    clickedMateId: Int = 0,
    isRounded: Boolean = false,
    onClickMate: (Mate) -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateCalendar: () -> Unit = {},
) {
    val shape =
        if (isRounded) RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp) else RectangleShape
    val topPadding = if (isRounded) 32.dp else 10.dp
    val weekDates = listOf(
        LocalDate.now(),
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(2),
        LocalDate.now().plusDays(3),
        LocalDate.now().plusDays(4),
        LocalDate.now().plusDays(5),
        LocalDate.now().plusDays(6)
    )
    var selectedDate by remember { mutableStateOf(weekDates[2]) }

    Surface(
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .background(YakssokTheme.color.grey50)
                .padding(top = topPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MateLazyRow(
                mateList = mateList,
                clickedMateId = clickedMateId,
                onNavigateMate = onNavigateMate,
                onMateClick = onClickMate
            )
            Spacer(modifier = Modifier.height(8.dp))
            WeekDataSelector(
                weekDates = weekDates,
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it },
                onNavigateCalendar = onNavigateCalendar
            )
        }
    }
}