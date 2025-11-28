package com.pillsquad.yakssok.feature.home.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
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
import com.pillsquad.yakssok.feature.home.HomeSkeleton
import com.pillsquad.yakssok.feature.home.R
import com.pillsquad.yakssok.feature.home.model.FADE_DURATION
import com.pillsquad.yakssok.feature.home.model.HIGHLIGHT_DURATION
import com.pillsquad.yakssok.feature.home.model.HomeState
import com.pillsquad.yakssok.feature.home.model.OVERLAY_ALPHA_DIM
import com.pillsquad.yakssok.feature.home.model.OVERLAY_ALPHA_FULL
import com.pillsquad.yakssok.feature.home.model.OVERLAY_FADE_DURATION
import com.pillsquad.yakssok.feature.home.model.TutorialTransitionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TutorialColumn(
    modifier: Modifier = Modifier,
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

    var isExplainVisible by remember(highlightRect) { mutableStateOf(false) }

    val transitionState = rememberTutorialTransitionState(
        tutorialStep = state.tutorialStep,
        currentTargetKey = state.currentTargetKey,
        highlightRect = highlightRect,
        changeExplainVisible = { isExplainVisible = true }
    )

    val animatedHighlight = transitionState.currentRect

    val textTopDp = remember(highlightRect) {
        highlightRect?.let {
            with(density) { it.bottom.toDp() } + 24.dp
        } ?: 24.dp
    }

    val textBottomDp = remember(highlightRect, screenHeightPx) {
        highlightRect?.let {
            with(density) { screenHeightPx.toDp() - it.top.toDp() } + 24.dp
        } ?: 24.dp
    }

    Box(modifier = modifier.fillMaxSize()) {
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
                overlayAlpha = transitionState.overlayAlpha,
                highlightRect = animatedHighlight,
                onClickNextStep = onClickNextStep
            )

            if (!state.currentTargetKey.shouldHideExplain() && isExplainVisible) {
                Column(
                    modifier = Modifier
                        .align(state.currentTargetKey.loc.alignment)
                        .graphicsLayer { alpha = transitionState.explainAlpha },
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExplainText(
                        modifier = Modifier.padding(
                            start = 32.dp,
                            top = if (state.currentTargetKey.loc.isTop()) textTopDp else 0.dp,
                            end = 32.dp,
                            bottom = if (!state.currentTargetKey.loc.isTop()) textBottomDp else 0.dp
                        ),
                        firstContent = stringResource(state.currentTargetKey.textGroupRes.firstContent),
                        highlightContent = stringResource(state.currentTargetKey.textGroupRes.highlightContent),
                        secondContent = stringResource(state.currentTargetKey.textGroupRes.secondContent)
                    )

                    if (state.currentTargetKey == TutorialTargetKey.NOTIFICATION_COMPLETE) {
                        YakssokButton(
                            modifier = Modifier.padding(horizontal = 72.dp),
                            text = stringResource(R.string.tutorial_button),
                            contentColor = YakssokTheme.color.grey50,
                            onClick = onClickNextStep
                        )
                    }
                }
            }

            if (state.currentTargetKey.isNotificationKey()) {
                ExampleNotification(
                    density = density,
                    onMeasure = onMeasure
                )
            }
        }
    }
}

@Composable
private fun rememberTutorialTransitionState(
    tutorialStep: Int,
    currentTargetKey: TutorialTargetKey,
    highlightRect: Rect?,
    changeExplainVisible: () -> Unit
): TutorialTransitionState {
    val state = remember {
        TutorialTransitionState(
            initialOverlayAlpha = OVERLAY_ALPHA_FULL,
            initialRect = Rect.Zero
        )
    }

    var previousStep by remember { mutableIntStateOf(tutorialStep) }
    var previousHighlight by remember { mutableStateOf<Rect?>(null) }

    LaunchedEffect(tutorialStep, highlightRect) {
        val fromStep = previousStep
        val toStep = tutorialStep
        val fromRect = previousHighlight
        val toRect = highlightRect

        val targetAlpha = when (currentTargetKey) {
            TutorialTargetKey.EMPTY,
            TutorialTargetKey.END -> 0f

            TutorialTargetKey.NOTIFICATION -> OVERLAY_ALPHA_DIM
            TutorialTargetKey.NOTIFICATION_COMPLETE -> OVERLAY_ALPHA_FULL
            else -> OVERLAY_ALPHA_FULL
        }

        val isMovingHighlightStep =
            (fromStep == 0 && toStep == 1 || fromStep == 1 && toStep == 2) && fromRect != null && toRect != null

        launch {
            state.explainAlphaAnim.snapTo(0f)

            changeExplainVisible()

            when (toStep) {
                0 -> state.explainAlphaAnim.snapTo(1f)
                4, 6 -> {
                    state.explainAlphaAnim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = FADE_DURATION)
                    )
                }

                else -> {
                    delay((HIGHLIGHT_DURATION * 0.9).toLong())
                    state.explainAlphaAnim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = FADE_DURATION)
                    )
                }
            }
        }

        launch {
            state.overlayAlphaAnim.animateTo(
                targetValue = targetAlpha,
                animationSpec = tween(durationMillis = OVERLAY_FADE_DURATION)
            )
        }

        if (isMovingHighlightStep) {
            launch {
                state.rectAnim.animateTo(
                    targetValue = toRect,
                    animationSpec = tween(
                        durationMillis = HIGHLIGHT_DURATION,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        } else {
            state.rectAnim.snapTo(toRect ?: Rect.Zero)
        }

        previousStep = toStep
        previousHighlight = toRect
    }

    return state
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
        }
    ) {
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
    overlayAlpha: Float = 0.7f,
    highlightRect: Rect?,
    onClickNextStep: () -> Unit
) {
    val overlayRadiusPx = 16.dp.toPx(density)
    val overlayBaseColor = YakssokTheme.color.black
    val interactionSource = remember { MutableInteractionSource() }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickNextStep
            )
            .drawWithCache {
                val overlayPath = Path().apply {
                    addRect(Rect(0f, 0f, size.width, size.height))
                    highlightRect?.let { rect ->
                        if (rect.width > 0 && rect.height > 0) {
                            addRoundRect(
                                RoundRect(
                                    rect = rect,
                                    cornerRadius = CornerRadius(overlayRadiusPx, overlayRadiusPx)
                                )
                            )
                        }
                    }
                    fillType = PathFillType.EvenOdd
                }

                onDrawBehind {
                    drawPath(
                        path = overlayPath,
                        color = overlayBaseColor.copy(alpha = overlayAlpha)
                    )
                }
            }
    )
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
            .padding(10.dp),
        text = annotatedText,
        style = YakssokTheme.typography.body1
    )
}

@Composable
private fun ExampleNotification(
    density: Density,
    onMeasure: (Rect) -> Unit
) {
    var measuredRect by remember { mutableStateOf<Rect?>(null) }

    Row(
        modifier = Modifier
            .padding(top = 72.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val newRect = coordinates.toRect(density)
                if (measuredRect != newRect) {
                    measuredRect = newRect
                    onMeasure(newRect)
                }
            }
            .clip(RoundedCornerShape(16.dp))
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