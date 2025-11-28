package com.pillsquad.yakssok.feature.mymate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.R
import com.pillsquad.yakssok.core.ui.component.ErrorScreen
import com.pillsquad.yakssok.core.ui.component.LoadingScreen
import com.pillsquad.yakssok.core.ui.component.MateItem
import com.pillsquad.yakssok.core.ui.ext.OnResumeEffect
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.mymate.model.MyMateUiState

@Composable
internal fun MyMateRoute(
    viewModel: MyMateViewModel = hiltViewModel(),
    onNavigateMate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnResumeEffect { viewModel.getMateList() }

    MyMateScreen(
        uiState = uiState,
        onNavigateMate = onNavigateMate,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun MyMateScreen(
    uiState: MyMateUiState,
    onNavigateMate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val mateListSize = (uiState as? MyMateUiState.Success)?.mateList?.size ?: 0

    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            title = "메이트",
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        MateTitle(
            mateCount = mateListSize,
            onNavigateMate = onNavigateMate
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            MyMateUiState.Empty -> EmptyScreen()
            MyMateUiState.Failure -> ErrorScreen()
            MyMateUiState.Loading -> LoadingScreen()
            is MyMateUiState.Success -> MyMateContent(uiState.mateList)
        }
    }
}

@Composable
private fun MyMateContent(
    mateList: List<User>,
) {
    val maxWidth = LocalWindowInfo.current.containerSize.width.dp
    val itemWidth = 64.dp + 8.dp
    val itemsPerRow = (maxWidth / itemWidth).toInt().coerceAtLeast(1)

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        mateList.forEach { mate ->
            Box(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                MateItem(
                    user = mate,
                    imgSize = 64,
                )
            }
        }

        repeat(mateList.size % itemsPerRow) {
            Spacer(modifier = Modifier.width(itemWidth))
        }
    }
}

@Composable
private fun MateTitle(
    title: String = "내 메이트",
    mateCount: Int,
    onNavigateMate: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey800
            )

            Box(
                modifier = Modifier
                    .background(
                        color = YakssokTheme.color.grey150,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 2.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = "${mateCount}명",
                    style = YakssokTheme.typography.body1,
                    color = YakssokTheme.color.grey800
                )
            }
        }

        AddButton(onClick = onNavigateMate)
    }
}

@Composable
private fun AddButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(YakssokTheme.color.grey150)
            .size(28.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_mate),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "메이트를 추가하고 함께 약을 챙겨보세요!",
            color = YakssokTheme.color.grey700
        )
    }
}