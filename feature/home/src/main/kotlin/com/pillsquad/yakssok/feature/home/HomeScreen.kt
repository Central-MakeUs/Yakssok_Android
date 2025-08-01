package com.pillsquad.yakssok.feature.home

import android.util.SparseArray
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
import androidx.compose.runtime.Composable
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
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.component.DailyMedicineList
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.component.NoMedicineColumn
import com.pillsquad.yakssok.feature.home.component.FeedbackDialog
import com.pillsquad.yakssok.feature.home.component.UserInfoCard
import com.pillsquad.yakssok.feature.home.component.WeekDataSelector
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

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

    var feedbackTarget by remember { mutableStateOf<User?>(null) }

    feedbackTarget?.let {
        FeedbackDialog(
            user = it,
            todayRoutineCount = uiState.userList[uiState.selectedUserIdx].notTakenCount,
            routineList = uiState.routineCache[uiState.selectedUserIdx]?.get(uiState.selectedDate) ?: emptyList(),
            onDismiss = { feedbackTarget = null },
            onConfirm = { userId, message, type ->
                viewModel.postFeedback(userId, message, type)
                feedbackTarget = null
            }
        )
    }

    HomeScreen(
        showFeedBackSection = uiState.showFeedBackSection,
        selectedDate = uiState.selectedDate,
        userProfileList = uiState.userList,
        routineCache = uiState.routineCache,
        scrollState = scrollState,
        selectedUserIdx = uiState.selectedUserIdx,
        onClickUser = {
            viewModel.onMateClick(it)
        },
        onSelectDate = {
            viewModel.onSelectedDate(it)
        },
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

@Composable
private fun HomeScreen(
    showFeedBackSection: Boolean = false,
    selectedDate: LocalDate = LocalDate.today(),
    userProfileList: List<User> = emptyList(),
    routineCache: SparseArray<MutableMap<LocalDate, List<Routine>>> = SparseArray(),
    selectedUserIdx: Int = 0,
    scrollState: ScrollState = rememberScrollState(),
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey50)
            .systemBarsPadding()
    ) {
        YakssokTopAppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            isLogo = true,
            onNavigateAlert = onNavigateAlert,
            onNavigateMy = onNavigateMy
        )

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
                        if (userProfileList[index].notTakenCount != null) {
                            UserInfoCard(
                                nickName = userProfileList[index].nickName,
                                relationName = userProfileList[index].relationName,
                                profileUrl = userProfileList[index].profileImage,
                                remainedMedicine = userProfileList[index].notTakenCount ?: 0,
                                onClick = {
                                    onSendMessage(userProfileList[index])
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