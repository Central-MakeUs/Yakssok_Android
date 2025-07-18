package com.pillsquad.yakssok.feature.mypage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.mypage.component.InfoRow
import com.pillsquad.yakssok.feature.mypage.component.MyPageBottomButtons
import com.pillsquad.yakssok.feature.mypage.component.PillMateRow
import com.pillsquad.yakssok.feature.mypage.component.ProfileEditButton
import com.pillsquad.yakssok.feature.mypage.component.ProfileRow
import com.pillsquad.yakssok.feature.mypage.model.MyPageUiState

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
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onNavigateProfileEdit = onNavigateProfileEdit,
        onNavigateMyRoutine = onNavigateMyRoutine,
        onNavigateMyMate = onNavigateMyMate,
        onNavigateInfo = onNavigateInfo
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageUiState,
    onNavigateBack: () -> Unit,
    onNavigateProfileEdit: () -> Unit,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
    onNavigateInfo: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            onBackClick = onNavigateBack,
        )

        when (uiState) {
            MyPageUiState.Loading -> {
                Text(text = "Loading")
            }
            is MyPageUiState.Success -> {
                SuccessContent(
                    imgUrl = uiState.data.imgUrl,
                    name = uiState.data.name,
                    routineCount = uiState.data.routineCount,
                    mateCount = uiState.data.mateCount,
                    appVersion = uiState.data.appVersion,
                    onNavigateProfileEdit = onNavigateProfileEdit,
                    onNavigateMyRoutine = onNavigateMyRoutine,
                    onNavigateMyMate = onNavigateMyMate,
                    onNavigateInfo = onNavigateInfo
                )
            }
            is MyPageUiState.Error -> {
                Text(text = "error")
            }
        }
    }
}

@Composable
private fun SuccessContent(
    imgUrl: String,
    name: String,
    routineCount: Int,
    mateCount: Int,
    appVersion: String,
    onNavigateProfileEdit: () -> Unit,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
    onNavigateInfo: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileRow(imgUrl = imgUrl, name = name)
            ProfileEditButton(onNavigateProfileEdit = onNavigateProfileEdit)
        }

        Spacer(modifier = Modifier.height(20.dp))

        PillMateRow(
            routineCount = routineCount,
            mateCount = mateCount,
            onNavigateMyRoutine = onNavigateMyRoutine,
            onNavigateMyMate = onNavigateMyMate
        )

        Spacer(modifier = Modifier.height(32.dp))

        InfoRow(
            iconRes = R.drawable.ic_cotact_support,
            title = "개인정보 정책",
            onClick = { onNavigateInfo("개인정보정책", "notionUrl") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoRow(
            iconRes = R.drawable.ic_error,
            title = "이용약관",
            onClick = { onNavigateInfo("이용정책", "notionUrl") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = YakssokTheme.color.grey200,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "앱 버전",
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey600
            )

            Text(
                text = appVersion,
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey400
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        MyPageBottomButtons(
            onLogout = {},
            onAccountCancel = {}
        )
    }
}