package com.pillsquad.yakssok.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.util.isEmpty
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.DailyMedicineList
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.component.NoMedicineColumn
import com.pillsquad.yakssok.core.ui.ext.OnResumeEffect
import com.pillsquad.yakssok.feature.calendar.component.Calendar
import com.pillsquad.yakssok.feature.calendar.model.CalendarUiModel
import kotlinx.datetime.LocalDate

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateRoutine: () -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateMyPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnResumeEffect {
        viewModel.loadUserAndRoutines()
    }

    CalendarScreen(
        uiState = uiState,
        onUpdateSelectedDate = viewModel::updateSelectedDate,
        onClickUser = viewModel::onMateClick,
        onLoadDataForPage = viewModel::loadDataForPage,
        onRoutineClick = viewModel::onRoutineClick,
        onNavigateBack = onNavigateBack,
        onNavigateRoutine = onNavigateRoutine,
        onNavigateAlert = onNavigateAlert,
        onNavigateMate = onNavigateMate,
        onNavigateMyPage = onNavigateMyPage
    )
}

@Composable
internal fun CalendarScreen(
    uiState: CalendarUiModel = CalendarUiModel(),
    onUpdateSelectedDate: (LocalDate) -> Unit = {},
    onClickUser: (Int) -> Unit = {},
    onLoadDataForPage: (Int) -> Unit = {},
    onRoutineClick: (Int) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateRoutine: () -> Unit = {},
    onNavigateAlert: () -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateMyPage: () -> Unit = {},
) {
    val modifier = Modifier.padding(horizontal = 16.dp)
    val today = LocalDate.today()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey50)
            .systemBarsPadding()
    ) {
        YakssokTopAppBar(
            modifier = modifier,
            onBackClick = onNavigateBack,
            onNavigateAlert = onNavigateAlert,
            onNavigateMy = onNavigateMyPage
        )

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                MateLazyRow(
                    modifier = modifier,
                    userList = uiState.userList,
                    selectedUserIdx = uiState.selectedUserIdx,
                    onNavigateMate = onNavigateMate,
                    onMateClick = onClickUser
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Calendar(
                    modifier = modifier,
                    selectedDate = uiState.selectedDate,
                    takenCache = uiState.takenCache[uiState.selectedUserIdx] ?: emptyMap(),
                    onDateSelected = onUpdateSelectedDate,
                    onLoadDataForPage = onLoadDataForPage
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                        .background(YakssokTheme.color.grey100)
                )
            }


            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                if (uiState.routineCache.isEmpty()) {
                    NoMedicineColumn(
                        modifier = modifier,
                        isNeverAlarm = false,
                        onNavigateToRoutine = onNavigateRoutine
                    )
                } else {
                    val isCheckBoxVisible = (uiState.selectedUserIdx == 0) && (uiState.selectedDate == today)

                    DailyMedicineList(
                        modifier = modifier,
                        isCheckBoxVisible = isCheckBoxVisible,
                        routineList = uiState.routineCache[uiState.selectedUserIdx]?.get(uiState.selectedDate) ?: emptyList(),
                        onItemClick = onRoutineClick,
                        onNavigateToRoute = onNavigateRoutine
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}