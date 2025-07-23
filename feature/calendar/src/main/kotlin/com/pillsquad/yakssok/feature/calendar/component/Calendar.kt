package com.pillsquad.yakssok.feature.calendar.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.feature.calendar.model.CalendarConfig
import com.pillsquad.yakssok.feature.calendar.model.today
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.number

@Composable
internal fun Calendar(
    modifier: Modifier = Modifier,
    today: LocalDate = LocalDate.today(),
    selectedDate: LocalDate = today,
    takenCache: Map<LocalDate, Boolean>,
    config: CalendarConfig = CalendarConfig(),
    onDateSelected: (LocalDate) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val initialPage =
        (selectedDate.year - config.yearRange.first) * 12 + selectedDate.month.number - 1
    val totalPages = (config.yearRange.last - config.yearRange.first + 1) * 12

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { totalPages }
    )
    var currentPage by remember { mutableIntStateOf(initialPage) }

    val currentYearMonth by remember(pagerState.currentPage) {
        derivedStateOf {
            val year = config.yearRange.first + (pagerState.currentPage / 12)
            val month = (pagerState.currentPage % 12) + 1
            YearMonth(year, month)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != currentPage) {
            currentPage = pagerState.currentPage
            // TODO: pagerState에 따라서 범위에 따른 데이터 값을 미리 불러오는 로직
//            loadDataForPage(currentPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        CalendarHeader(
            yearMonth = currentYearMonth,
            onPreviousMonth = {
                scope.launch {
                    val targetPage = pagerState.currentPage - 1
                    if (targetPage >= 0) {
                        pagerState.animateScrollToPage(targetPage)
                    }
                }
            },
            onNextMonth = {
                scope.launch {
                    val targetPage = pagerState.currentPage + 1
                    if (targetPage < totalPages) {
                        pagerState.animateScrollToPage(targetPage)
                    }
                }
            }
        )

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) { page ->
            val date = YearMonth(
                year = config.yearRange.first + (page / 12),
                month = (page % 12) + 1
            )

            CalendarMonthItem(
                today = today,
                currentYearMonth = date,
                selectedDate = selectedDate,
                takenCache = takenCache,
                onDateSelected = onDateSelected
            )
        }
    }
}