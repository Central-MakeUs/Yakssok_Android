package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.PullToRefreshColumn
import com.pillsquad.yakssok.core.ui.ext.getAlignmentByLocation
import com.pillsquad.yakssok.core.ui.ext.toPx
import com.pillsquad.yakssok.core.ui.model.Location
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey
import com.pillsquad.yakssok.feature.home.HomeSkeleton
import com.pillsquad.yakssok.feature.home.model.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TutorialColumn(
    uiState: HomeUiState,
    isTutorialNeed: Boolean,
    highlightRect: Rect? = null,
    targetKey: TutorialTargetKey,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    scaleFraction: () -> Float = { 1f },
    onNavigateAlert: () -> Unit = {},
    onNavigateMyPage: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onSuccess: @Composable (state: HomeUiState.Success) -> Unit,
) {
    val density = LocalDensity.current
    val screenHeightDp = LocalWindowInfo.current.containerSize.height

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PullToRefreshColumn(
            refreshState = refreshState,
            isRefreshing = isRefreshing,
            scaleFraction = scaleFraction,
            onRefresh = onRefresh,
            topBar = {
                YakssokTopAppBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    isLogo = true,
                    onNavigateAlert = onNavigateAlert,
                    onNavigateMy = onNavigateMyPage
                )
            }
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> HomeSkeleton(showFeedbackSection = true)
                is HomeUiState.Success -> {
                    onSuccess(state)
                }
            }
        }

        if (isTutorialNeed) {
            val isEmpty = targetKey == TutorialTargetKey.EMPTY

            TutorialOverlay(
                isOverlay = !isEmpty,
                highlightRect = highlightRect,
                onNextClick = onNextClick
            )

            if (!isEmpty && highlightRect != null) {
                val textTopDp = with(density) { highlightRect.bottom.toDp() } + 24.dp
                val textBottomDp = with(density) { screenHeightDp.toDp() - highlightRect.top.toDp() } + 24.dp

                ExplainText(
                    modifier = Modifier
                        .align(getAlignmentByLocation(targetKey.loc))
                        .padding(
                            start = 32.dp,
                            top = if (isLocationTop(targetKey.loc)) textTopDp else 0.dp,
                            end = 32.dp,
                            bottom = if (!isLocationTop(targetKey.loc)) textBottomDp else 0.dp
                        ),
                    firstContent = stringResource(targetKey.textGroupRes.firstContent),
                    highlightContent = stringResource(targetKey.textGroupRes.highlightContent),
                    secondContent = stringResource(targetKey.textGroupRes.secondContent)
                )
            }
        }
    }
}

@Composable
private fun TutorialOverlay(
    isOverlay: Boolean = true,
    highlightRect: Rect?,
    onNextClick: () -> Unit
) {
    val density = LocalDensity.current
    val overlayColor = if (isOverlay) {
        YakssokTheme.color.black.copy(alpha = 0.7f)
    } else {
        Color.Transparent
    }
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

@Composable
private fun ExplainText(
    modifier: Modifier = Modifier,
    firstContent: String = "",
    secondContent: String = "",
    highlightContent: String
) {
    val normalStyle = SpanStyle(color = YakssokTheme.color.grey50)
    val highlightStyle = SpanStyle(color = YakssokTheme.color.primary300)

    // body1이 기본 스타일 => SpanStyle의 color만 override
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(YakssokTheme.color.grey900)
            .padding(10.dp),
        text = buildAnnotatedString {
            withStyle(style = normalStyle) { append(firstContent) }
            withStyle(style = highlightStyle) { append(highlightContent) }
            withStyle(style = normalStyle) { append(secondContent) }
        },
        style = YakssokTheme.typography.body1
    )
}

private fun isLocationTop(loc: Location): Boolean {
    return loc == Location.TOP_START || loc == Location.TOP_END || loc == Location.TOP_CENTER
}
