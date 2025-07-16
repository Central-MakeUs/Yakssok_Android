package com.pillsquad.yakssok.feature.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.alert.model.MessageUiModel

@Composable
internal fun AlertRoute(
    viewModel: AlertViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlertScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun AlertScreen(
    uiState: List<MessageUiModel>,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            title = "알림",
            onBackClick = onNavigateBack
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState) {

            }
        }
    }
}