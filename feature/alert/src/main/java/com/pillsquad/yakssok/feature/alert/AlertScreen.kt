package com.pillsquad.yakssok.feature.alert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.alert.component.MessageBubble

@Composable
internal fun AlertRoute(
    viewModel: AlertViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    AlertScreen(
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun AlertScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            title = "알림",
            onBackClick = onNavigateBack
        )
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MessageBubble("일이삼사오육칠팔구십일이삼사오")
        }
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth().weight(1f)
//        ) {
//
//        }
    }
}