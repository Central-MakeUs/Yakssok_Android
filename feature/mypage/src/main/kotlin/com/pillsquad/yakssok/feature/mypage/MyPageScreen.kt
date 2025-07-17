package com.pillsquad.yakssok.feature.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateProfileEdit: () -> Unit,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
    onNavigateInfo: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        onNavigateBack = onNavigateBack,
        onNavigateProfileEdit = onNavigateProfileEdit,
        onNavigateMyRoutine = onNavigateMyRoutine,
        onNavigateMyMate = onNavigateMyMate,
        onNavigateInfo = onNavigateInfo
    )
}

@Composable
private fun MyPageScreen(
    onNavigateBack: () -> Unit,
    onNavigateProfileEdit: () -> Unit,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
    onNavigateInfo: (String, String) -> Unit
) {

}