package com.pillsquad.yakssok.feature.routine

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.routine.component.CompleteDialog
import com.pillsquad.yakssok.feature.routine.component.EndDateDialog
import com.pillsquad.yakssok.feature.routine.component.IntakeCountDialog
import com.pillsquad.yakssok.feature.routine.component.IntakeDayDialog
import com.pillsquad.yakssok.feature.routine.component.NumberIndicator
import com.pillsquad.yakssok.feature.routine.component.StartDateDialog
import com.pillsquad.yakssok.feature.routine.component.TimeDialog
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
import com.pillsquad.yakssok.feature.routine.util.today
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun RoutineRoute(
    viewModel: RoutineViewModel = hiltViewModel(),
    name: String,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = null)

    var selectedStartDate: LocalDate? by remember { mutableStateOf(null) }
    var selectedEndDate: LocalDate? by remember { mutableStateOf(null) }
    var selectedIntakeDays: List<WeekType> by remember { mutableStateOf(listOf()) }
    var selectedIntakeCount: Int by remember { mutableIntStateOf(0) }
    var selectedIntakeTimeIdx: Int by remember { mutableIntStateOf(-1) }
    var selectedIntakeTime: LocalTime? by remember { mutableStateOf(null) }

    var isEndDateShow by remember { mutableStateOf(false) }
    var isIntakeDaysShow by remember { mutableStateOf(false) }
    var isComplete by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    viewModel.stopSoundPool()
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            viewModel.stopSoundPool()
        }
    }

    BackHandler {
        if (uiState.curPage == 2) viewModel.stopSoundPool()

        if (uiState.curPage > 0) {
            viewModel.updateCurPage(uiState.curPage - 1)
        } else {
            onNavigateBack()
        }
    }

    LaunchedEffect(event) {
        when(event) {
            RoutineEvent.NavigateBack -> onNavigateBack()
            is RoutineEvent.ShowToast -> {}
            null -> Unit
        }
    }

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
                selectedIntakeCount = uiState.intakeCount
            },
            onValueChange = {
                selectedIntakeDays = it
            }
        )
    }

    if (selectedIntakeCount > 0) {
        IntakeCountDialog(
            selectedCount = selectedIntakeCount,
            onDismiss = { selectedIntakeCount = 0 },
            onConfirm = {
                viewModel.updateIntakeCount(selectedIntakeCount)
                selectedIntakeCount = 0
                selectedIntakeTimeIdx = 0
                selectedIntakeTime = uiState.intakeTimes[0]
            },
            onValueChange = {
                selectedIntakeCount = it
            }
        )
    }

    if (selectedIntakeTimeIdx >= 0) {
        val currentTime = selectedIntakeTime ?: uiState.intakeTimes[selectedIntakeTimeIdx]

        key(selectedIntakeTimeIdx) {
            TimeDialog(
                selectedIdx = selectedIntakeTimeIdx,
                selectedTime = currentTime,
                onDismiss = {
                    selectedIntakeTimeIdx = -1
                    selectedIntakeTime = null
                },
                onConfirm = {
                    viewModel.updateIntakeTime(currentTime, selectedIntakeTimeIdx)
                    if (selectedIntakeTimeIdx < uiState.intakeCount - 1) {
                        val nextIdx = selectedIntakeTimeIdx + 1
                        selectedIntakeTimeIdx = -1
                        selectedIntakeTime = uiState.intakeTimes.getOrNull(nextIdx)
                        selectedIntakeTimeIdx = nextIdx
                    } else {
                        selectedIntakeTimeIdx = -1
                        selectedIntakeTime = null
                    }
                },
                onValueChange = {
                    selectedIntakeTime = it
                }
            )
        }
    }

    if (isComplete) {
        CompleteDialog(
            uiState = uiState,
            onConfirm = {
                isComplete = false
                viewModel.postMedication()
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
        onIntakeCountChange = {
            selectedIntakeCount = uiState.intakeCount
        },
        onIntakeTimeChange = {
            selectedIntakeTimeIdx = it
            selectedIntakeTime = uiState.intakeTimes[it]
        },
        onAlarmTypeChange = {
            viewModel.updateAlarmType(it)
        },
        onBackClick = {
            if (uiState.curPage == 2) viewModel.stopSoundPool()

            if (uiState.curPage > 0) {
                viewModel.updateCurPage(uiState.curPage - 1)
            } else {
                onNavigateBack()
            }
        },
        onNextClick = {
            if (uiState.curPage < 2) {
                viewModel.updateCurPage(uiState.curPage + 1)
            } else {
                isComplete = true
            }
        }
    )
}

@Composable
internal fun RoutineScreen(
    userName: String,
    uiState: RoutineUiModel,
    onPillNameChange: (String) -> Unit,
    onPillTypeChange: (MedicationType) -> Unit,
    onStartDateChange: () -> Unit,
    onEndDateChange: () -> Unit,
    onIntakeDayChange: () -> Unit,
    onIntakeCountChange: () -> Unit,
    onIntakeTimeChange: (Int) -> Unit,
    onAlarmTypeChange: (AlarmType) -> Unit,
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
            when (uiState.curPage) {
                0 -> FirstContent(
                    userName = userName,
                    pillName = uiState.pillName,
                    selectedMedicationType = uiState.medicationType,
                    onPillNameChange = onPillNameChange,
                    onPillTypeChange = onPillTypeChange
                )
                1 -> SecondContent(
                    startDate = uiState.startDate,
                    endDate = uiState.endDate,
                    intakeCount = uiState.intakeCount,
                    intakeDays = uiState.intakeDays,
                    intakeTimes = uiState.intakeTimes,
                    onStartDateChange = onStartDateChange,
                    onEndDateChange = onEndDateChange,
                    onIntakeCountChange = onIntakeCountChange,
                    onIntakeDaysChange = onIntakeDayChange,
                    onIntakeTimesChange = onIntakeTimeChange
                )
                else -> ThirdContent(
                    selectedAlarmType = uiState.alarmType,
                    onAlarmTypeChange = onAlarmTypeChange
                )
            }
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