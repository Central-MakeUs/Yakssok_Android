package com.pillsquad.yakssok.feature.mymate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault

@Composable
internal fun MyMateRoute(
    viewModel: MyMateViewModel = hiltViewModel(),
    onNavigateMate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyMateScreen(
        followingList = uiState.followingList,
        followerList = uiState.followerList,
        onNavigateMate = onNavigateMate,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun MyMateScreen(
    followingList: List<User> = emptyList(),
    followerList: List<User> = emptyList(),
    onNavigateMate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            title = "메이트",
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        MateTitle(
            title = "팔로잉",
            mateCount = followingList.size
        )

        Spacer(modifier = Modifier.height(16.dp))

        MateLazyRow(
            userList = followingList,
            imgSize = 64,
            iconSize = 24,
            iconButtonColor = YakssokTheme.color.grey150,
            onNavigateMate = onNavigateMate
        )

        Spacer(modifier = Modifier.height(32.dp))

        MateTitle(
            title = "팔로워",
            mateCount = followerList.size
        )

        Spacer(modifier = Modifier.height(16.dp))

        MateLazyRow(
            userList = followerList,
            imgSize = 64,
            iconSize = 24,
        )
    }
}

@Composable
private fun MateTitle(
    title: String,
    mateCount: Int
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
}