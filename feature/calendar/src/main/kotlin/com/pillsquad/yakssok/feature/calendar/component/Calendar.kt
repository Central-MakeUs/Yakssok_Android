package com.pillsquad.yakssok.feature.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.feature.calendar.model.CalendarConfig
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
    onLoadDataForPage: (Int) -> Unit = {}
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

    val loadedPages = remember { mutableStateSetOf(currentPage) }

    val currentYearMonth by remember(pagerState.currentPage) {
        derivedStateOf {
            val year = config.yearRange.first + (pagerState.currentPage / 12)
            val month = (pagerState.currentPage % 12) + 1
            YearMonth(year, month)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val pagesToLoad = listOf(
            pagerState.currentPage,
            pagerState.currentPage - 1,
            pagerState.currentPage + 1
        ).filter { it in 0 until totalPages }

        for (page in pagesToLoad) {
            if (!loadedPages.contains(page)) {
                onLoadDataForPage(page)
                loadedPages.add(page)
            }
        }

        if (currentPage != pagerState.currentPage) {
            currentPage = pagerState.currentPage
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
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