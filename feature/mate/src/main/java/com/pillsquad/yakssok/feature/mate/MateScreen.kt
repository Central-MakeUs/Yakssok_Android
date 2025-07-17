package com.pillsquad.yakssok.feature.mate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun MateRoute(
    viewModel: MateViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {

    MateScreen(
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun MateScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey100)
            .statusBarsPadding()
    ) {
        YakssokTopAppBar(
            title = "메이트 추가",
            onBackClick = onNavigateBack
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = YakssokTheme.color.grey50,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 44.dp, bottom = 16.dp)
        ) {

        }
    }

}