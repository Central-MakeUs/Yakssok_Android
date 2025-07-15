package com.pillsquad.yakssok.feature.home

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
import androidx.compose.material3.Surface
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
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.designsystem.util.shadow
import com.pillsquad.yakssok.core.model.Mate
import com.pillsquad.yakssok.core.model.Medicine
import com.pillsquad.yakssok.core.ui.component.DailyMedicineList
import com.pillsquad.yakssok.core.ui.component.MateLazyRow
import com.pillsquad.yakssok.core.ui.component.NoMedicineColumn
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.home.component.UserInfoCard
import com.pillsquad.yakssok.feature.home.component.WeekDataSelector
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateRoutine: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    HomeScreen(
        scrollState = scrollState,
        mateList = listOf(
            Mate(
                id = 1,
                name = "임용수",
                nickName = "나",
                profileImage = "https://picsum.photos/200",
                remainedMedicine = 1
            ),
            Mate(
                id = 2,
                name = "조앵",
                nickName = "PM",
                profileImage = "https://picsum.photos/200",
                remainedMedicine = 0
            ),
            Mate(
                id = 3,
                name = "리아",
                nickName = "iOS",
                profileImage = "https://picsum.photos/200",
                remainedMedicine = 3
            ),
            Mate(
                id = 4,
                name = "노을",
                nickName = "Server",
                profileImage = "https://picsum.photos/200",
                remainedMedicine = 3
            )
        ),
        onNavigateRoutine = { onNavigateRoutine("김OO") }
    )
}

@Composable
private fun HomeScreen(
    isRounded: Boolean = false,
    mateList: List<Mate> = emptyList(),
    medicineList: List<Medicine> = emptyList(),
    clickedMateId: Int = 0,
    scrollState: ScrollState = rememberScrollState(),
    onClickMate: (Mate) -> Unit = {},
    onSendMessage: (Mate) -> Unit = {},
    onNavigateMy: () -> Unit = {},
    onNavigateMate: () -> Unit = {},
    onNavigateAlarm: () -> Unit = {},
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
            onNavigateAlarm = onNavigateAlarm,
            onNavigateMy = onNavigateMy
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(YakssokTheme.color.grey100)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Spacer(modifier = Modifier.width(16.dp))
                }
                items(mateList.size) { index ->
                    UserInfoCard(
                        name = mateList[index].name,
                        nickName = mateList[index].nickName,
                        profileUrl = mateList[index].profileImage,
                        remainedMedicine = mateList[index].remainedMedicine,
                        onClick = {
                            onClickMate(mateList[index])
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeContent(
                modifier = Modifier,
                mateList = mateList,
                medicineList = medicineList,
                clickedMateId = clickedMateId,
                isRounded = true,
                onClickMate = onClickMate,
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
    mateList: List<Mate>,
    medicineList: List<Medicine>,
    clickedMateId: Int,
    isRounded: Boolean,
    onClickMate: (Mate) -> Unit,
    onNavigateMate: () -> Unit,
    onNavigateRoutine: () -> Unit,
    onNavigateCalendar: () -> Unit,
) {
    val shape =
        if (isRounded) RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp) else RectangleShape
    val topPadding = if (isRounded) 32.dp else 10.dp
    val weekDates = listOf(
        LocalDate.now(),
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(2),
        LocalDate.now().plusDays(3),
        LocalDate.now().plusDays(4),
        LocalDate.now().plusDays(5),
        LocalDate.now().plusDays(6)
    )
    var selectedDate by remember { mutableStateOf(weekDates[2]) }

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
            mateList = mateList,
            clickedMateId = clickedMateId,
            onNavigateMate = onNavigateMate,
            onMateClick = onClickMate
        )
        Spacer(modifier = Modifier.height(8.dp))
        WeekDataSelector(
            weekDates = weekDates,
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            onNavigateCalendar = onNavigateCalendar
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (medicineList.isEmpty()) {
            NoMedicineColumn(
                modifier = Modifier,
                isNeverAlarm = false,
                onNavigateToRoutine = onNavigateRoutine
            )
        } else {
            DailyMedicineList(
                medicineList = medicineList,
                onItemClick = {},
                onNavigateToRoute = onNavigateRoutine
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}