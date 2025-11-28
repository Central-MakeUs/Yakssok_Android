package com.pillsquad.yakssok.feature.home.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
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
import com.pillsquad.yakssok.feature.home.HomeSkeleton
import com.pillsquad.yakssok.feature.home.R
import com.pillsquad.yakssok.feature.home.model.HomeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val RectVectorConverter = TwoWayConverter<Rect, AnimationVector4D>(
    convertToVector = { rect ->
        AnimationVector4D(rect.left, rect.top, rect.right, rect.bottom)
    },
    convertFromVector = { vector ->
        Rect(vector.v1, vector.v2, vector.v3, vector.v4)
    }
)

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

    val rectAnim = remember {
        Animatable(
            initialValue = Rect(0f, 0f, 0f, 0f),
            typeConverter = RectVectorConverter
        )
    }
    val overlayAlphaAnim = remember { Animatable(0.7f) }
    val explainAlphaAnim = remember { Animatable(0f) }

    var previousStep by remember { mutableIntStateOf(state.tutorialStep) }
    var previousHighlight by remember { mutableStateOf<Rect?>(null) }

    var isVisible by remember(highlightRect) { mutableStateOf(false) }

    val animatedHighlight: Rect? = remember(highlightRect, rectAnim.value) {
        if (highlightRect == null) {
            null
        } else {
            val current = rectAnim.value
            if (current.width == 0f && current.height == 0f) {
                // 애니메이션 최초 실행 전에는 highlightRect를 그대로 사용
                highlightRect
            } else {
                current
            }
        }
    }

    // step에 따른 overlayAlpha, explainAlpha, highlightRect 애니메이션 처리
    LaunchedEffect(state.tutorialStep) {
        val fromStep = previousStep
        val toStep = state.tutorialStep
        val fromRect = previousHighlight
        val toRect = highlightRect

        // overlayAlpha 애니메이션 (2→3, 3→4, 4→5, 5→6 포함 전체 step 대응)
        val targetAlpha = when (state.currentTargetKey) {
            TutorialTargetKey.EMPTY,
            TutorialTargetKey.END -> 0f

            TutorialTargetKey.NOTIFICATION -> 0.3f
            TutorialTargetKey.NOTIFICATION_COMPLETE -> 0.7f

            else -> 0.7f
        }

        val isMovingHighlightStep =
            (fromStep == 0 && toStep == 1 || fromStep == 1 && toStep == 2) && fromRect != null && toRect != null

        val highlightDuration = 700

        launch {
            explainAlphaAnim.snapTo(0f)

            isVisible = true

            when (toStep) {
                0 -> explainAlphaAnim.snapTo(1f)

                4, 6 -> {
                    explainAlphaAnim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 250)
                    )
                }

                else -> {

                    delay((highlightDuration * 0.9).toLong())
                    explainAlphaAnim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 250)
                    )
                }
            }
        }

        launch {
            overlayAlphaAnim.animateTo(
                targetValue = targetAlpha,
                animationSpec = tween(durationMillis = 300)
            )
        }

        if (isMovingHighlightStep) {
            // Rect 이동 애니메이션
            launch {
                rectAnim.animateTo(
                    targetValue = toRect,
                    animationSpec = tween(
                        durationMillis = highlightDuration,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        } else {
            // Rect 이동 애니메이션이 필요 없는 경우: 그냥 스냅
            rectAnim.snapTo(toRect ?: Rect(0f, 0f, 0f, 0f))
        }

        previousStep = toStep
        previousHighlight = toRect
    }

    val textTopDp = remember(highlightRect) {
        highlightRect?.let {
            with(density) { it.bottom.toDp() } + 24.dp
        } ?: 24.dp
    }

    val textBottomDp = remember(highlightRect) {
        highlightRect?.let {
            with(density) { screenHeightPx.toDp() - it.top.toDp() } + 24.dp
        } ?: 24.dp
    }

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
                overlayAlaph = overlayAlphaAnim.value,
                highlightRect = animatedHighlight,
                onClickNextStep = onClickNextStep
            )

            if (!state.currentTargetKey.shouldHideExplain() && isVisible) {
                Column(
                    modifier = Modifier
                        .align(state.currentTargetKey.loc.alignment),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExplainText(
                        modifier = Modifier
                            .padding(
                                start = 32.dp,
                                top = if (state.currentTargetKey.loc.isTop()) textTopDp else 0.dp,
                                end = 32.dp,
                                bottom = if (!state.currentTargetKey.loc.isTop()) textBottomDp else 0.dp
                            )
                            .alpha(explainAlphaAnim.value),
                        firstContent = stringResource(state.currentTargetKey.textGroupRes.firstContent),
                        highlightContent = stringResource(state.currentTargetKey.textGroupRes.highlightContent),
                        secondContent = stringResource(state.currentTargetKey.textGroupRes.secondContent)
                    )

                    if (state.currentTargetKey == TutorialTargetKey.NOTIFICATION_COMPLETE) {
                        YakssokButton(
                            modifier = Modifier
                                .padding(horizontal = 72.dp)
                                .alpha(explainAlphaAnim.value),
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
    overlayAlaph: Float = 0.7f,
    highlightRect: Rect?,
    onClickNextStep: () -> Unit
) {
    val overlayRadius = 16.dp.toPx(density)
    val overlayColor = YakssokTheme.color.black.copy(alpha = overlayAlaph)

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
    Row(
        modifier = Modifier
            .padding(top = 72.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .onGloballyPositioned {
                onMeasure(it.toRect(density))
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