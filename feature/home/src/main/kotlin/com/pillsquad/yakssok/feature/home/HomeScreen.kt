package com.pillsquad.yakssok.feature.home

import android.util.SparseArray
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.component.DailyMedicineList
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.component.NoMedicineColumn
import com.pillsquad.yakssok.core.ui.component.PullToRefreshColumn
import com.pillsquad.yakssok.core.ui.ext.OnResumeEffect
import com.pillsquad.yakssok.feature.home.component.FeedbackDialog
import com.pillsquad.yakssok.feature.home.component.RemindDialog
import com.pillsquad.yakssok.feature.home.component.UserInfoCard
import com.pillsquad.yakssok.feature.home.component.WeekDataSelector
import com.pillsquad.yakssok.feature.home.model.HomeUiState
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
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

    val scrollState = rememberScrollState()
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

    var feedbackTarget by remember { mutableStateOf<User?>(null) }

    OnResumeEffect {
        viewModel.refresh()
    }

    LaunchedEffect(uiState) {
        if (uiState is HomeUiState.Success) {
            isRefreshing = false
        }
    }

    feedbackTarget?.let { user ->
        FeedbackDialog(
            user = user,
            routineList = viewModel.getFeedbackList(user.id),
            onDismiss = { feedbackTarget = null },
            onConfirm = { userId, message, type ->
                viewModel.postFeedback(userId, message, type)
                feedbackTarget = null
            }
        )
    }

    when (val state = uiState) {
        HomeUiState.Loading -> {
            // 스켈레톤 UI가 짱인데..
        }
        is HomeUiState.Success -> {
            if (state.remindList.isNotEmpty()) {
                val nickName = state.userList[0].nickName

                RemindDialog(
                    name = nickName,
                    routineList = state.remindList,
                    onDismiss = { viewModel.clearRemindState() }
                )
            }

            HomeScreen(
                isRefreshing = isRefreshing,
                scaleFraction = scaleFraction,
                refreshState = refreshState,
                showFeedBackSection = state.showFeedBackSection,
                selectedDate = state.selectedDate,
                userProfileList = state.userList,
                routineCache = state.routineCache,
                selectedUserIdx = state.selectedUserIdx,
                scrollState = scrollState,
                onRefresh = onRefresh,
                onClickUser = viewModel::onMateClick,
                onSelectDate = viewModel::onSelectedDate,
                onClickRoutine = viewModel::onRoutineClick,
                onSendMessage = {
                    feedbackTarget = it
                },
                onNavigateMy = onNavigateMyPage,
                onNavigateMate = onNavigateMate,
                onNavigateAlert = onNavigateAlert,
                onNavigateRoutine = onNavigateRoutine,
                onNavigateCalendar = onNavigateCalendar
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    isRefreshing: Boolean = false,
    scaleFraction: () -> Float = { 1f },
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    showFeedBackSection: Boolean = false,
    selectedDate: LocalDate = LocalDate.today(),
    userProfileList: List<User> = emptyList(),
    routineCache: SparseArray<MutableMap<LocalDate, List<Routine>>> = SparseArray(),
    selectedUserIdx: Int = 0,
    scrollState: ScrollState = rememberScrollState(),
    onRefresh: () -> Unit = {},
    onClickUser: (Int) -> Unit = {},
    onSelectDate: (LocalDate) -> Unit = {},
    onClickRoutine: (Int) -> Unit = {},
    onSendMessage: (User) -> Unit = {},
    onNavigateMy: () -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateAlert: () -> Unit = {},
    onNavigateRoutine: () -> Unit = {},
    onNavigateCalendar: () -> Unit = {},
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
                onNavigateMy = onNavigateMy
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(YakssokTheme.color.grey100)
                .verticalScroll(scrollState)
        ) {


            if (showFeedBackSection) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    items(userProfileList.size) { index ->
                        val user = userProfileList[index]

                        if (user.notTakenCount != null) {
                            UserInfoCard(
                                id = user.id,
                                nickName = user.nickName,
                                relationName = user.relationName,
                                profileUrl = user.profileImage,
                                remainedMedicine = user.notTakenCount ?: 0,
                                onClick = {
                                    onSendMessage(user)
                                }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            HomeContent(
                modifier = Modifier,
                userProfileList = userProfileList,
                routineList = routineCache[selectedUserIdx]?.get(selectedDate) ?: emptyList(),
                selectedDate = selectedDate,
                selectedUserIdx = selectedUserIdx,
                isRounded = showFeedBackSection,
                isNotMedicine = userProfileList[selectedUserIdx].isNotMedicine,
                onClickUser = onClickUser,
                onSelectDate = onSelectDate,
                onClickRoutine = onClickRoutine,
                onNavigateMate = onNavigateMate,
                onNavigateRoutine = onNavigateRoutine,
                onNavigateCalendar = onNavigateCalendar
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    userProfileList: List<User>,
    routineList: List<Routine>,
    selectedDate: LocalDate,
    selectedUserIdx: Int,
    isRounded: Boolean,
    isNotMedicine: Boolean,
    onClickUser: (Int) -> Unit,
    onSelectDate: (LocalDate) -> Unit = {},
    onClickRoutine: (Int) -> Unit = {},
    onNavigateMate: () -> Unit,
    onNavigateRoutine: () -> Unit,
    onNavigateCalendar: () -> Unit,
) {
    val shape =
        if (isRounded) RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp) else RectangleShape
    val topPadding = if (isRounded) 32.dp else 10.dp

    val today = LocalDate.today()
    val weekDates = remember {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        (0..6).map { monday.plus(it, DateTimeUnit.DAY) }
    }

    Column(
        modifier = modifier
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
            onMateClick = { onClickUser(it) }
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
                modifier = Modifier,
                isNeverAlarm = isNotMedicine,
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