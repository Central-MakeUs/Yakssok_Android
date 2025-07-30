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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.util.isEmpty
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.DailyMedicineList
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.component.NoMedicineColumn
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
    val scrollState = rememberScrollState()

    CalendarScreen(
        uiState = uiState,
        scrollState = scrollState,
        onUpdateSelectedDate = viewModel::updateSelectedDate,
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
    scrollState: ScrollState = rememberScrollState(),
    onUpdateSelectedDate: (LocalDate) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateRoutine: () -> Unit = {},
    onNavigateAlert: () -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateMyPage: () -> Unit = {},
) {
    val modifier = Modifier.padding(horizontal = 16.dp)

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
                    userList = uiState.userLists,
                    selectedUserIdx = uiState.selectedMate,
                    onNavigateMate = onNavigateMate,
                    onMateClick = {}
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Calendar(
                    modifier = modifier,
                    selectedDate = uiState.selectedDate,
                    takenCache = uiState.takenCache[uiState.selectedMate] ?: emptyMap(),
                    onDateSelected = onUpdateSelectedDate
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
                if (uiState.medicineCache.isEmpty()) {
                    NoMedicineColumn(
                        modifier = modifier,
                        isNeverAlarm = false,
                        onNavigateToRoutine = onNavigateRoutine
                    )
                } else {
                    DailyMedicineList(
                        modifier = modifier,
                        medicineList = uiState.medicineCache[uiState.selectedMate]!!.values.flatten(),
                        onItemClick = {},
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