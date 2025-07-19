package com.pillsquad.yakssok.feature.myroutine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault

@Composable
internal fun MyRoutineRoute(
    viewModel: MyRoutineViewModel = hiltViewModel(),
    onNavigateRoutine: (String) -> Unit,
    onNavigateBack: () -> Unit
) {

}

@Composable
private fun MyRoutineScreen(
    onNavigateRoutine: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            title = "내 복약",
            onBackClick = onNavigateBack,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}