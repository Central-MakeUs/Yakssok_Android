package com.pillsquad.yakssok.feature.home

import android.util.SparseArray
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.designsystem.util.shadow
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.component.DailyMedicineList
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.component.NoMedicineColumn
import com.pillsquad.yakssok.core.ui.component.PullToRefreshColumn
import com.pillsquad.yakssok.core.ui.compositionlocal.LocalShowErrorSnackBar
import com.pillsquad.yakssok.core.ui.ext.CollectEvent
import com.pillsquad.yakssok.core.ui.ext.OnResumeEffect
import com.pillsquad.yakssok.feature.home.component.FeedbackDialog
import com.pillsquad.yakssok.feature.home.component.RemindDialog
import com.pillsquad.yakssok.feature.home.component.UserInfoCard
import com.pillsquad.yakssok.feature.home.component.WeekDataSelector
import com.pillsquad.yakssok.feature.home.model.HomeUiState
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateRoutine: () -> Unit,
    onNavigateAlert: () -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateMyPage: () -> Unit,
    onNavigateCalendar: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showSnackbar = LocalShowErrorSnackBar.current
    var feedbackTarget by remember { mutableStateOf<FeedbackTarget?>(null) }

    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(refreshState.distanceFraction).coerceIn(0f, 1f)
    }

    val onRefresh: () -> Unit = {
        isRefreshing = true
        viewModel.refresh()
    }

    OnResumeEffect { viewModel.refresh() }
    CollectEvent(viewModel.errorFlow) { showSnackbar(it) }

    LaunchedEffect(uiState) {
        if (uiState is HomeUiState.Success) {
            isRefreshing = false
        }
    }

    feedbackTarget?.let { feedback ->
        FeedbackDialog(
            feedback = feedback,
            onDismiss = { feedbackTarget = null },
            onConfirm = { userId, message, type ->
                viewModel.postFeedback(userId, message, type)
                feedbackTarget = null
            }
        )
    }

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
                state.remindList.firstOrNull()?.let {
                    RemindDialog(
                        name = state.userList.firstOrNull()?.nickName.orEmpty(),
                        routineList = state.remindList,
                        onDismiss = viewModel::clearRemindState
                    )
                }

                HomeScreen(
                    state = state,
                    onClickUser = viewModel::onMateClick,
                    onSelectDate = viewModel::onSelectedDate,
                    onClickRoutine = viewModel::onRoutineClick,
                    onClickFeedback = { feedbackTarget = it },
                    onNavigateMate = onNavigateMate,
                    onNavigateRoutine = onNavigateRoutine,
                    onNavigateCalendar = onNavigateCalendar
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    state: HomeUiState.Success,
    onClickUser: (Int) -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    onClickRoutine: (Int) -> Unit,
    onClickFeedback: (FeedbackTarget) -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateRoutine: () -> Unit,
    onNavigateCalendar: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val showFeedbackSection by remember(state.feedbackTargetList) {
        derivedStateOf { state.feedbackTargetList.isNotEmpty() }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(YakssokTheme.color.grey100)
            .verticalScroll(scrollState)
    ) {
        if (showFeedbackSection) {
            FeedbackSection(
                feedbackTargetList = state.feedbackTargetList,
                onClickFeedback = onClickFeedback
            )
        }

        HomeContent(
            userProfileList = state.userList,
            routineCache = state.routineCache,
            selectedDate = state.selectedDate,
            selectedUserIdx = state.selectedUserIdx,
            isRounded = showFeedbackSection,
            onClickUser = onClickUser,
            onSelectDate = onSelectDate,
            onClickRoutine = onClickRoutine,
            onNavigateMate = onNavigateMate,
            onNavigateRoutine = onNavigateRoutine,
            onNavigateCalendar = onNavigateCalendar
        )
    }
}

@Composable
private fun FeedbackSection(
    feedbackTargetList: List<FeedbackTarget>,
    onClickFeedback: (FeedbackTarget) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        item { Spacer(modifier = Modifier.width(16.dp)) }

        items(feedbackTargetList.size) { index ->
            val feedbackTarget = feedbackTargetList[index]

            UserInfoCard(
                feedback = feedbackTarget,
                onClick = { onClickFeedback(feedbackTarget) }
            )

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun HomeContent(
    userProfileList: List<User>,
    routineCache: SparseArray<MutableMap<LocalDate, List<Routine>>>,
    selectedDate: LocalDate,
    selectedUserIdx: Int,
    isRounded: Boolean,
    onClickUser: (Int) -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    onClickRoutine: (Int) -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateRoutine: () -> Unit,
    onNavigateCalendar: () -> Unit
) {
    val shape =
        if (isRounded) RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp) else RectangleShape
    val topPadding = if (isRounded) 32.dp else 10.dp

    val today = LocalDate.today()
    val weekDates by remember {
        derivedStateOf { calculateCurrentWeek(today) }
    }

    val selectedUser = userProfileList.getOrNull(selectedUserIdx) ?: return
    val routineList = routineCache[selectedUserIdx]?.get(selectedDate) ?: emptyList()

    Column(
        modifier = Modifier
            .shadow(
                offsetX = 0.dp,
                offsetY = 4.dp,
                blur = 12.dp,
                color = Color.Black.copy(alpha = 0.15f),
            )
            .clip(shape)
            .background(YakssokTheme.color.grey50)
            .padding(top = topPadding, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MateLazyRow(
            userList = userProfileList,
            selectedUserIdx = selectedUserIdx,
            onNavigateMate = onNavigateMate,
            onMateClick = onClickUser
        )
        Spacer(modifier = Modifier.height(8.dp))
        WeekDataSelector(
            weekDates = weekDates,
            selectedDate = selectedDate,
            onDateSelected = onSelectDate,
            onNavigateCalendar = onNavigateCalendar
        )
        Spacer(modifier = Modifier.height(32.dp))

        if (routineList.isEmpty()) {
            NoMedicineColumn(
                isNeverAlarm = selectedUser.isNotMedicine,
                onNavigateToRoutine = onNavigateRoutine
            )
        } else {
            val isCheckBoxVisible = (selectedUserIdx == 0) && (selectedDate == today)
            DailyMedicineList(
                routineList = routineList,
                isCheckBoxVisible = isCheckBoxVisible,
                onItemClick = onClickRoutine,
                onNavigateToRoute = onNavigateRoutine
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

private fun calculateCurrentWeek(today: LocalDate): List<LocalDate> {
    val monday = today.minus(today.dayOfWeek.ordinal.toLong(), DateTimeUnit.DAY)
    return List(7) { i -> monday.plus(i.toLong(), DateTimeUnit.DAY) }
}