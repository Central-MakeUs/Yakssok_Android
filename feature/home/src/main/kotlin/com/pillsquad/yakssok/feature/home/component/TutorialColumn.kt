package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.PullToRefreshColumn
import com.pillsquad.yakssok.core.ui.ext.getAlignmentByLocation
import com.pillsquad.yakssok.core.ui.ext.toPx
import com.pillsquad.yakssok.core.ui.ext.toRect
import com.pillsquad.yakssok.core.ui.model.Location
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey
import com.pillsquad.yakssok.feature.home.HomeSkeleton
import com.pillsquad.yakssok.feature.home.R
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
    onMeasure: (Rect) -> Unit = { _ -> },
    dialog: @Composable () -> Unit = {},
    onSuccess: @Composable (state: HomeUiState.Success) -> Unit
) {
    val density = LocalDensity.current
    val screenHeightPx = LocalWindowInfo.current.containerSize.height
    var explainRect by remember { mutableStateOf<Rect?>(null) }

    val textTopDp = remember(highlightRect, density) {
        highlightRect?.let { with(density) { it.bottom.toDp() } + 24.dp } ?: 24.dp
    }

    val textBottomDp = remember(highlightRect, density, screenHeightPx) {
        highlightRect?.let {
            with(density) { screenHeightPx.toDp() - it.top.toDp() } + 24.dp
        } ?: 24.dp
    }

    val explainTopDp = remember(explainRect, textTopDp, density) {
        explainRect?.let { with(density) { it.bottom.toDp() + 20.dp } } ?: textTopDp
    }

    val isEmpty = targetKey == TutorialTargetKey.EMPTY

    Box(
        modifier = Modifier.fillMaxSize()
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
                is HomeUiState.Success -> onSuccess(state)
            }
        }

        dialog()

        if (isTutorialNeed) {
            TutorialOverlay(
                density = density,
                isOverlay = !isEmpty,
                highlightRect = highlightRect,
                onNextClick = onNextClick
            )

            if (!isEmpty) {
                ExplainText(
                    modifier = Modifier
                        .align(getAlignmentByLocation(targetKey.loc))
                        .padding(
                            start = 32.dp,
                            top = if (targetKey.loc.isTop) textTopDp else 0.dp,
                            end = 32.dp,
                            bottom = if (!targetKey.loc.isTop) textBottomDp else 0.dp
                        )
                        .onGloballyPositioned {
                            explainRect = it.toRect(density)
                        },
                    firstContent = stringResource(targetKey.textGroupRes.firstContent),
                    highlightContent = stringResource(targetKey.textGroupRes.highlightContent),
                    secondContent = stringResource(targetKey.textGroupRes.secondContent)
                )
            }

            if (targetKey == TutorialTargetKey.NOTIFICATION) {
                ExampleNotification(
                    modifier = Modifier
                        .padding(top = 72.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            onMeasure(it.toRect(density))
                        }
                )

                YakssokButton(
                    modifier = Modifier
                        .padding(
                            start = 72.dp,
                            end = 72.dp,
                            top = explainTopDp,
                        ),
                    text = stringResource(R.string.tutorial_button),
                    contentColor = YakssokTheme.color.grey50,
                    onClick = onNextClick
                )
            }
        }
    }
}

@Composable
private fun TutorialOverlay(
    density: Density,
    isOverlay: Boolean = true,
    highlightRect: Rect?,
    onNextClick: () -> Unit
) {
    val overlayRadius = 16.dp.toPx(density)
    val overlayColor = if (isOverlay) {
        YakssokTheme.color.black.copy(alpha = 0.7f)
    } else {
        Color.Transparent
    }

    var canvasSize by remember { mutableStateOf(Size.Zero) }
    val overlayPath = remember(highlightRect, canvasSize, overlayRadius) {
        Path().apply {
            addRect(Rect(0f, 0f, canvasSize.width, canvasSize.height))

            highlightRect?.let {
                addRoundRect(
                    RoundRect(
                        rect = it,
                        cornerRadius = CornerRadius(overlayRadius, overlayRadius)
                    )
                )
            }

            fillType = PathFillType.EvenOdd
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size -> canvasSize = size.toSize() }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onNextClick
            )
    ) {
        drawPath(
            path = overlayPath,
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

    val annotatedText = remember(firstContent, highlightContent, secondContent) {
        buildAnnotatedString {
            withStyle(style = normalStyle) { append(firstContent) }
            withStyle(style = highlightStyle) { append(highlightContent) }
            withStyle(style = normalStyle) { append(secondContent) }
        }
    }

    // body1이 기본 스타일 => SpanStyle의 color만 override
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(YakssokTheme.color.grey900)
            .padding(10.dp),
        text = annotatedText,
        style = YakssokTheme.typography.body1
    )
}

@Composable
private fun ExampleNotification(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(21.dp))
            .background(YakssokTheme.color.grey150)
            .padding(start = 9.dp, end = 14.dp, top = 17.dp, bottom = 17.dp)
    ) {
        YakssokImage(
            modifier = Modifier.size(45.dp),
            shape = RoundedCornerShape(8.dp),
            contentDescription = "약쏙 알림 예시"
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    color = YakssokTheme.color.black,
                    style = YakssokTheme.typography.subtitle2,
                    text = "김약쏙 님이 잔소리해요!"
                )
                Text(
                    color = YakssokTheme.color.grey800,
                    style = YakssokTheme.typography.body2,
                    text = "약 까먹었네? 얼른 먹어! \uD83D\uDC8A"
                )
            }
            Text(
                color = YakssokTheme.color.grey400,
                style = YakssokTheme.typography.body2,
                text = "3:00 pm"
            )
        }
    }
}

private val Location.isTop: Boolean
    get() = this == Location.TOP_START ||
            this == Location.TOP_END ||
            this == Location.TOP_CENTER