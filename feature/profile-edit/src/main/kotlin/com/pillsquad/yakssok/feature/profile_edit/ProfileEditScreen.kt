package com.pillsquad.yakssok.feature.profile_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.profileEdit.R
import com.pillsquad.yakssok.feature.profile_edit.model.ProfileEditUiModel

@Composable
internal fun ProfileEditRoute(
    viewModel: ProfileEditViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileEditScreen(
        uiState = uiState,
        onImageUpdate = viewModel::updateImgUrl,
        onValueChange = viewModel::updateName,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun ProfileEditScreen(
    uiState: ProfileEditUiModel,
    onImageUpdate: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YakssokTopAppBar(
            title = "프로필",
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(27.dp))

        Box(
            modifier = Modifier.clickable(
                onClick = {},
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
        ) {
            YakssokImage(
                modifier = Modifier.size(100.dp).align(Alignment.Center),
                imageUrl = uiState.imgUrl,
            )

            Icon(
                modifier = Modifier.align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.ic_profile_edit),
                contentDescription = "profile edit",
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(27.dp))

        YakssokTextField(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = YakssokTheme.color.grey50,
            value = uiState.name,
            onValueChange = onValueChange,
            hint = "닉네임",
            isShowClear = true,
            isShowCounter = true,
        )

        Spacer(modifier = Modifier.weight(1f))

        YakssokButton(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            text = "변경 완료",
            enabled = uiState.enabled,
            backgroundColor = if (uiState.enabled) YakssokTheme.color.primary400 else YakssokTheme.color.grey200,
            contentColor = if (uiState.enabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400,
            onClick = onNavigateBack,
        )
    }
}