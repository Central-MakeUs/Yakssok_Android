package com.pillsquad.yakssok.feature.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.routine.component.DatePicker
import com.pillsquad.yakssok.feature.routine.component.NumberIndicator
import com.pillsquad.yakssok.feature.routine.component.TimePicker
import com.pillsquad.yakssok.feature.routine.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
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

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var selectedStartDate: LocalDate? by remember { mutableStateOf(null) }
    var selectedEndDate: LocalDate? by remember { mutableStateOf(null) }
    var isEndDateShow by remember { mutableStateOf(false) }

    if (selectedStartDate != null) {
        YakssokDialog(
            title = "복용 시작 날짜를 설정해주세요",
            content = {
                DatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    initialDate = selectedStartDate ?: uiState.startDate,
                    onValueChange = {
                        selectedStartDate = it
                    }
                )
            },
            enabled = selectedStartDate?.let {
                it >= today
            } ?: false,
            onDismiss = { selectedStartDate = null },
            onConfirm = {
                viewModel.updateStartDate(selectedStartDate ?: uiState.startDate)
                selectedStartDate = null
                selectedEndDate = uiState.endDate ?: Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                isEndDateShow = true
            }
        )
    }

    if (isEndDateShow) {
        var noEndDate by remember { mutableStateOf(false) }

        YakssokDialog(
            title = "복용 종료 날짜를 설정해주세요",
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    DatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        initialDate = selectedEndDate ?: Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                        onValueChange = {
                            selectedEndDate = it
                            noEndDate = false
                        }
                    )
                    NoEndDateCheckBox(
                        isTrued = noEndDate,
                        onClick = {
                            noEndDate = !noEndDate
                            selectedEndDate = if (noEndDate) {
                                null
                            } else {
                                today
                            }
                        }
                    )
                }
            },
            enabled = if (noEndDate) {
                true
            } else {
                val startDate = uiState.startDate
                val endDate = selectedEndDate
                if (endDate == null) {
                    false
                } else {
                    endDate >= startDate
                }
            },
            onDismiss = {
                selectedEndDate = null
                isEndDateShow = false
            },
            onConfirm = {
                val finalEndDate = if (noEndDate) null else selectedEndDate
                viewModel.updateEndDate(finalEndDate)
                selectedEndDate = null
                isEndDateShow = false
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
                onIntakeDaysChange = {},
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

@Composable
private fun NoEndDateCheckBox(
    isTrued: Boolean,
    onClick: () -> Unit
) {
    val icResource = if (isTrued) R.drawable.ic_check_on else R.drawable.ic_check_off

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "종료일 없음",
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey950
        )
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = icResource),
                contentDescription = "check box",
                tint = Color.Unspecified
            )
        }
    }
}