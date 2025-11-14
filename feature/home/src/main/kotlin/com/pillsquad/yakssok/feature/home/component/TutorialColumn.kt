package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.customInsets
import com.pillsquad.yakssok.core.ui.ext.toPx
import com.pillsquad.yakssok.feature.home.HomeSkeleton
import com.pillsquad.yakssok.feature.home.model.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TutorialColumn(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    isTutorialNeed: Boolean,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    isRefreshing: Boolean = false,
    highlightRect: Rect? = null,
    onRefresh: () -> Unit = {},
    scaleFraction: () -> Float = { 1f },
    onNavigateAlert: () -> Unit = {},
    onNavigateMyPage: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onSuccess: @Composable (state: HomeUiState.Success) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(YakssokTheme.color.grey50)
                .customInsets(top = true, bottom = true)
                .pullToRefresh(
                    state = refreshState,
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh
                )
        ) {
            YakssokTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                isLogo = true,
                onNavigateAlert = onNavigateAlert,
                onNavigateMy = onNavigateMyPage
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (uiState) {
                    is HomeUiState.Loading -> HomeSkeleton(showFeedbackSection = true)
                    is HomeUiState.Success -> { onSuccess(uiState) }
                }

                Box(
                    Modifier
                        .align(Alignment.TopCenter)
                        .graphicsLayer {
                            scaleX = scaleFraction()
                            scaleY = scaleFraction()
                        }
                ) {
                    PullToRefreshDefaults.Indicator(
                        state = refreshState,
                        isRefreshing = isRefreshing
                    )
                }
            }
        }

        if (isTutorialNeed) {
            TutorialOverlay(
                highlightRect = highlightRect,
                onNextClick = onNextClick
            )
        }
    }
}

@Composable
private fun TutorialOverlay(
    highlightRect: Rect?,
    onNextClick: () -> Unit
) {
    val density = LocalDensity.current
    val overlayColor = YakssokTheme.color.black.copy(alpha = 0.7f)
    val overlayRadius = 16.dp.toPx(density)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onNextClick
            )
    ) {
        val path = Path().apply {
            // 전체 영역
            addRect(Rect(0f, 0f, size.width, size.height))

            // 하이라이트 영역 빼기
            highlightRect?.let {
                addRoundRect(
                    RoundRect(
                        rect = it,
                        cornerRadius = CornerRadius(overlayRadius, overlayRadius)
                    )
                )
            }

            // 차집합 (EvenOdd)
            fillType = PathFillType.EvenOdd
        }

        drawPath(
            path = path,
            color = overlayColor
        )
    }
}