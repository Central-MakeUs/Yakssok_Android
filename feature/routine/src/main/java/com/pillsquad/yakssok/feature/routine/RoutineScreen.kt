package com.pillsquad.yakssok.feature.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.routine.component.EndDateDialog
import com.pillsquad.yakssok.feature.routine.component.IntakeDayDialog
import com.pillsquad.yakssok.feature.routine.component.NumberIndicator
import com.pillsquad.yakssok.feature.routine.component.StartDateDialog
import com.pillsquad.yakssok.feature.routine.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
import com.pillsquad.yakssok.feature.routine.picker.DatePicker
import com.pillsquad.yakssok.feature.routine.util.today
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun RoutineRoute(
    viewModel: RoutineViewModel = hiltViewModel(),
    name: String,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedStartDate: LocalDate? by remember { mutableStateOf(null) }
    var selectedEndDate: LocalDate? by remember { mutableStateOf(null) }
    var selectedIntakeDays: List<WeekType> by remember { mutableStateOf(listOf()) }
    var isEndDateShow by remember { mutableStateOf(false) }
    var isIntakeDaysShow by remember { mutableStateOf(false) }

    if (selectedStartDate != null) {
        StartDateDialog(
            uiStartDate = uiState.startDate,
            selectedStartDate = selectedStartDate,
            onDismiss = { selectedStartDate = null },
            onConfirm = {
                viewModel.updateStartDate(it)
                selectedStartDate = null
                selectedEndDate = uiState.endDate ?: LocalDate.today()
                isEndDateShow = true
            },
            onValueChange = {
                selectedStartDate = it
            }
        )
    }

    if (isEndDateShow) {
        EndDateDialog(
            uiStartDate = uiState.startDate,
            selectedEndDate = selectedEndDate,
            onDismiss = {
                selectedEndDate = null
                isEndDateShow = false
            },
            onConfirm = {
                viewModel.updateEndDate(it)
                selectedEndDate = null
                isEndDateShow = false
                selectedIntakeDays = uiState.intakeDays
                isIntakeDaysShow = true
            },
            onValueChange = {
                selectedEndDate = it
            }
        )
    }

    if (isIntakeDaysShow) {
        IntakeDayDialog(
            selectedDay = selectedIntakeDays,
            onDismiss = {
                selectedIntakeDays = listOf()
                isIntakeDaysShow = false
            },
            onConfirm = {
                viewModel.updateIntakeDays(selectedIntakeDays)
                selectedIntakeDays = listOf()
                isIntakeDaysShow = false
            },
            onValueChange = {
                selectedIntakeDays = it
            }
        )
    }

    RoutineScreen(
        userName = name,
        uiState = uiState,
        onPillNameChange = viewModel::updatePillName,
        onPillTypeChange = viewModel::updatePillType,
        onStartDateChange = {
            selectedStartDate = uiState.startDate
        },
        onEndDateChange = {
            isEndDateShow = true
            selectedEndDate = uiState.startDate
        },
        onIntakeDayChange = {
            isIntakeDaysShow = true
            selectedIntakeDays = uiState.intakeDays
        },
        onBackClick = onNavigateBack,
        onNextClick = onNavigateBack
    )
}

@Composable
internal fun RoutineScreen(
    userName: String,
    uiState: RoutineUiModel,
    onPillNameChange: (String) -> Unit,
    onPillTypeChange: (PillType) -> Unit,
    onStartDateChange: () -> Unit,
    onEndDateChange: () -> Unit,
    onIntakeDayChange: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val curEnabled = uiState.enabled[uiState.curPage]

    Box(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        Column(
            modifier = Modifier.background(Color.Transparent)
        ) {
            YakssokTopAppBar(onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(16.dp))
            NumberIndicator(curPage = uiState.curPage)
            Spacer(modifier = Modifier.height(32.dp))
            SecondContent(
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                intakeCount = uiState.intakeCount,
                intakeDays = uiState.intakeDays,
                intakeTimes = uiState.intakeTimes,
                onStartDateChange = onStartDateChange,
                onEndDateChange = onEndDateChange,
                onIntakeCountChange = {},
                onIntakeDaysChange = onIntakeDayChange,
                onIntakeTimesChange = {}
            )
        }
        YakssokButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .imePadding(),
            text = stringResource(R.string.next_button),
            enabled = curEnabled,
            backgroundColor = if (curEnabled) YakssokTheme.color.primary400 else YakssokTheme.color.grey200,
            contentColor = if (curEnabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400,
            onClick = onNextClick,
        )
    }
}