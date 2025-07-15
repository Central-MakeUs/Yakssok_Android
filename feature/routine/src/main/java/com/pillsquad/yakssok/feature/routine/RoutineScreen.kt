package com.pillsquad.yakssok.feature.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.routine.component.NumberIndicator
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun RoutineRoute(
    viewModel: RoutineViewModel = hiltViewModel(),
    name: String,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RoutineScreen(
        userName = name,
        uiState = uiState,
        onPillNameChange = viewModel::updatePillName,
        onPillTypeChange = viewModel::updatePillType,
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
            FirstContent(
                userName = userName,
                pillName = uiState.pillName,
                selectedPillType = uiState.pillType,
                onPillNameChange = onPillNameChange,
                onPillTypeChange = onPillTypeChange
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
