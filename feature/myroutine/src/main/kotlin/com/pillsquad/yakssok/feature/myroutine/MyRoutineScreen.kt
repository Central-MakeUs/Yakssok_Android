package com.pillsquad.yakssok.feature.myroutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.myroutine.component.InfoCard
import com.pillsquad.yakssok.feature.myroutine.component.RoutinePlusButton
import com.pillsquad.yakssok.feature.myroutine.model.PillUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun MyRoutineRoute(
    viewModel: MyRoutineViewModel = hiltViewModel(),
    onNavigateRoutine: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val tabs = listOf("전체", "복약 전", "복약 중", "복약 종료")

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val tabIndex = pagerState.currentPage

    MyRoutineScreen(
        tabs = tabs,
        tabIndex = tabIndex,
        pagerState = pagerState,
        scope = scope,
        fullList = uiState.value,
        onNavigateRoutine = onNavigateRoutine,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyRoutineScreen(
    tabs: List<String>,
    tabIndex: Int,
    pagerState: PagerState,
    scope: CoroutineScope,
    fullList: List<PillUiModel>,
    onNavigateRoutine: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey100)
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        YakssokTopAppBar(
            title = "내 복약",
            onBackClick = onNavigateBack,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryTabRow(
            modifier = Modifier.fillMaxWidth(),
            containerColor = YakssokTheme.color.grey100,
            contentColor = YakssokTheme.color.grey950,
            selectedTabIndex = tabIndex,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabIndex),
                    color = YakssokTheme.color.grey950
                )
            }
        ) {
            tabs.forEachIndexed { idx, value ->
                Tab(
                    selected = tabIndex == idx,
                    selectedContentColor = YakssokTheme.color.grey950,
                    unselectedContentColor = YakssokTheme.color.grey400,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(idx) }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = value,
                        style = YakssokTheme.typography.body1,
                    )
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) { page ->
            val filteredList = when (page) {
                1 -> fullList.filter { it.medicationStatus == MedicationStatus.PLANNED }
                2 -> fullList.filter { it.medicationStatus == MedicationStatus.TAKING }
                3 -> fullList.filter { it.medicationStatus == MedicationStatus.ENDED }
                else -> fullList
            }

            RoutineList(
                items = filteredList,
                onNavigateRoutine = onNavigateRoutine
            )
        }
    }
}

@Composable
private fun RoutineList(
    items: List<PillUiModel>,
    onNavigateRoutine: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                RoutinePlusButton(onClick = onNavigateRoutine)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(items) {
            InfoCard(it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}