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
import com.pillsquad.yakssok.core.ui.ext.toPx
import com.pillsquad.yakssok.core.ui.ext.toRect
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey.Companion.isNotificationKey
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey.Companion.shouldHideExplain
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey.Companion.shouldHideOverlay
import com.pillsquad.yakssok.feature.home.HomeSkeleton
import com.pillsquad.yakssok.feature.home.R
import com.pillsquad.yakssok.feature.home.model.HomeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TutorialColumn(
    state: HomeState,
    highlightRect: Rect? = null,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    onRefresh: () -> Unit = {},
    scaleFraction: () -> Float = { 1f },
    onNavigateAlert: () -> Unit = {},
    onNavigateMyPage: () -> Unit = {},
    onClickNextStep: () -> Unit = {},
    onMeasure: (Rect) -> Unit = { _ -> },
    dialog: @Composable () -> Unit = {},
    onSuccess: @Composable () -> Unit
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

    val isExplainVisible = !state.currentTargetKey.shouldHideExplain() && highlightRect != null
    val isNotificationVisible = state.currentTargetKey.isNotificationKey()
    val hideOverlay = state.currentTargetKey.shouldHideOverlay()
    val overlayAlpha = if (state.currentTargetKey == TutorialTargetKey.NOTIFICATION) 0.3f else 0.7f

    Box(modifier = Modifier.fillMaxSize()) {
        MainContent(
            state = state,
            refreshState = refreshState,
            scaleFraction = scaleFraction,
            onRefresh = onRefresh,
            onNavigateAlert = onNavigateAlert,
            onNavigateMyPage = onNavigateMyPage,
            onSuccess = onSuccess
        )

        dialog()

        if (!state.isTutorialComplete) {
            TutorialOverlay(
                density = density,
                isOverlay = !hideOverlay,
                overlayAlaph = overlayAlpha,
                highlightRect = highlightRect,
                onClickNextStep = onClickNextStep
            )

            if (isExplainVisible) {
                ExplainText(
                    modifier = Modifier
                        .align(state.currentTargetKey.loc.alignment)
                        .padding(
                            start = 32.dp,
                            top = if (state.currentTargetKey.loc.isTop()) textTopDp else 0.dp,
                            end = 32.dp,
                            bottom = if (!state.currentTargetKey.loc.isTop()) textBottomDp else 0.dp
                        )
                        .onGloballyPositioned {
                            explainRect = it.toRect(density)
                        },
                    firstContent = stringResource(state.currentTargetKey.textGroupRes.firstContent),
                    highlightContent = stringResource(state.currentTargetKey.textGroupRes.highlightContent),
                    secondContent = stringResource(state.currentTargetKey.textGroupRes.secondContent)
                )
            }

            if (isNotificationVisible) {
                ExampleNotification(
                    density = density,
                    onMeasure = onMeasure
                )
            }

            if (state.currentTargetKey == TutorialTargetKey.NOTIFICATION_COMPLETE) {
                YakssokButton(
                    modifier = Modifier.padding(
                        start = 72.dp,
                        end = 72.dp,
                        top = explainTopDp,
                    ),
                    text = stringResource(R.string.tutorial_button),
                    contentColor = YakssokTheme.color.grey50,
                    onClick = onClickNextStep
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    state: HomeState,
    refreshState: PullToRefreshState,
    scaleFraction: () -> Float,
    onRefresh: () -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMyPage: () -> Unit,
    onSuccess: @Composable () -> Unit
) {
    PullToRefreshColumn(
        refreshState = refreshState,
        isRefreshing = state.isRefreshing,
        scaleFraction = scaleFraction,
        onRefresh = onRefresh,
        topBar = {
            YakssokTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                isLogo = true,
                onNavigateAlert = onNavigateAlert,
                onNavigateMy = onNavigateMyPage
            )
        }) {
        if (state.isLoading) {
            HomeSkeleton(showFeedbackSection = true)
        } else {
            onSuccess()
        }
    }
}

@Composable
private fun TutorialOverlay(
    density: Density,
    isOverlay: Boolean = true,
    overlayAlaph: Float = 0.7f,
    highlightRect: Rect?,
    onClickNextStep: () -> Unit
) {
    val overlayRadius = 16.dp.toPx(density)
    val overlayColor = if (isOverlay) {
        YakssokTheme.color.black.copy(alpha = overlayAlaph)
    } else {
        Color.Transparent
    }

    val interactionSource = remember { MutableInteractionSource() }

    var canvasSize by remember { mutableStateOf(Size.Zero) }
    val overlayPath = remember(highlightRect, canvasSize, overlayRadius) {
        Path().apply {
            addRect(Rect(0f, 0f, canvasSize.width, canvasSize.height))
            highlightRect?.let {
                addRoundRect(
                    RoundRect(
                        rect = it, cornerRadius = CornerRadius(overlayRadius, overlayRadius)
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
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickNextStep
            )
    ) {
        drawPath(path = overlayPath, color = overlayColor)
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
            .clip(RoundedCornerShape(12.dp))
            .background(YakssokTheme.color.grey900)
            .padding(10.dp), text = annotatedText, style = YakssokTheme.typography.body1
    )
}

@Composable
private fun ExampleNotification(
    density: Density,
    onMeasure: (Rect) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 72.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .onGloballyPositioned {
                onMeasure(it.toRect(density))
            }
            .clip(RoundedCornerShape(21.dp))
            .background(YakssokTheme.color.grey150)
            .padding(start = 9.dp, end = 14.dp, top = 17.dp, bottom = 17.dp)
    ) {
        YakssokImage(
            modifier = Modifier.size(45.dp),
            shape = RoundedCornerShape(8.dp),
            contentDescription = stringResource(R.string.example_notification_iamge)
        )
        Spacer(modifier = Modifier.padding(12.dp))
        NotificationContent()
    }
}

@Composable
private fun NotificationContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                color = YakssokTheme.color.black,
                style = YakssokTheme.typography.subtitle2,
                text = stringResource(R.string.notification_title)
            )
            Text(
                color = YakssokTheme.color.grey800,
                style = YakssokTheme.typography.body2,
                text = stringResource(R.string.notification_content)
            )
        }
        Text(
            color = YakssokTheme.color.grey400,
            style = YakssokTheme.typography.body2,
            text = stringResource(R.string.notification_time)
        )
    }
}