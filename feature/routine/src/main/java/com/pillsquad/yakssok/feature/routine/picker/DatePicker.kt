package com.pillsquad.yakssok.feature.routine.picker

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.routine.util.today
import kotlinx.coroutines.delay
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
internal fun DatePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.today(),
    visibleItemsCount: Int = PickerDefaults.VISIBLE_ITEM_COUNT,
    style: PickerStyle = PickerDefaults.pickerStyle(),
    selector: PickerSelector = PickerDefaults.pickerSelector(),
    curveEffect: CurveEffect = PickerDefaults.curveEffect(),
    onValueChange: (LocalDate) -> Unit
) {
    val yearItems = remember { listOf(initialDate.year, initialDate.year + 1) }
    val monthItems = remember { (1..12).toList() }

    val yearPickerState = rememberPickerState(
        initialIndex = yearItems.indexOf(initialDate.year),
        items = yearItems
    )
    val monthPickerState = rememberPickerState(
        initialIndex = initialDate.month.number - 1,
        items = monthItems
    )

    val selectedYearIndex by yearPickerState.selectedIndex.collectAsStateWithLifecycle()
    val selectedMonthIndex by monthPickerState.selectedIndex.collectAsStateWithLifecycle()

    val dayPickerState = rememberPickerState(
        initialIndex = initialDate.day - 1,
        items = getDaysInMonth(initialDate.year, initialDate.month.number)
    )

    LaunchedEffect(selectedYearIndex, selectedMonthIndex) {
        if (dayPickerState.isUserScrolling.value) {
            return@LaunchedEffect
        }

        dayPickerState.suppressScrollSync.value = true

        val maxDays = getDaysInMonth(
            year = yearPickerState.selectedItem,
            month = monthPickerState.selectedItem
        )

        dayPickerState.updateItems(maxDays)

        val selectedDay = dayPickerState.selectedItem
        val safeDay = selectedDay.coerceAtMost(maxDays.last())
        val newIndex = maxDays.indexOf(safeDay)

        dayPickerState.updateSelectedIndex(newIndex)

        val scrollIndex = getStartIndexForInfiniteScroll(
            itemSize = maxDays.size,
            listScrollMiddle = Int.MAX_VALUE / 2,
            visibleItemsMiddle = PickerDefaults.VISIBLE_ITEM_COUNT / 2,
            startIndex = newIndex
        )

        dayPickerState.lazyListState.scrollToItem(scrollIndex)

        delay(200)
        dayPickerState.suppressScrollSync.value = false
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SelectorBackground(style = style, selector = selector)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PickerItem(
                state = yearPickerState,
                visibleItemsCount = visibleItemsCount,
                style = style,
                modifier = Modifier.weight(0.5f),
                infiniteScroll = false,
                curveEffect = curveEffect,
                onValueChange = {
                    onPickerValueChange(
                        yearState = yearPickerState,
                        monthState = monthPickerState,
                        dayState = dayPickerState,
                        onValueChange = onValueChange
                    )
                }
            )
            Row(
                modifier = Modifier
                    .weight(0.24f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                PickerItem(
                    modifier = Modifier.weight(0.9f),
                    state = monthPickerState,
                    visibleItemsCount = visibleItemsCount,
                    style = style,
                    infiniteScroll = true,
                    curveEffect = curveEffect,
                    onValueChange = {
                        onPickerValueChange(
                            yearState = yearPickerState,
                            monthState = monthPickerState,
                            dayState = dayPickerState,
                            onValueChange = onValueChange
                        )
                    }
                )
                Text(
                    modifier = Modifier.weight(0.3f),
                    text = "월",
                    style = YakssokTheme.typography.body0,
                    color = YakssokTheme.color.grey500,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier
                    .weight(0.24f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                PickerItem(
                    modifier = Modifier.weight(0.9f),
                    state = dayPickerState,
                    visibleItemsCount = visibleItemsCount,
                    style = style,
                    infiniteScroll = true,
                    itemFormatter = { it.toString().padStart(2, '0') },
                    curveEffect = curveEffect,
                    onValueChange = {
                        onPickerValueChange(
                            yearState = yearPickerState,
                            monthState = monthPickerState,
                            dayState = dayPickerState,
                            onValueChange = onValueChange
                        )
                    }
                )
                Text(
                    modifier = Modifier.weight(0.3f),
                    text = "일",
                    style = YakssokTheme.typography.body0,
                    color = YakssokTheme.color.grey500,
                )
            }
        }
    }

}

private fun getDaysInMonth(year: Int, month: Int): List<Int> {
    val lastDay = try {
        LocalDate(year, month, 1).plus(DatePeriod(months = 1))
            .minus(DatePeriod(days = 1))
            .day
    } catch (e: Exception) {
        Log.e("DatePicker", "getDaysInMonth: $e")
        30
    }
    return (1..lastDay).toList()
}

private fun onPickerValueChange(
    yearState: PickerState<Int>,
    monthState: PickerState<Int>,
    dayState: PickerState<Int>,
    onValueChange: (LocalDate) -> Unit
) {
    val year = yearState.selectedItem
    val month = monthState.selectedItem
    val maxDay = getDaysInMonth(year, month).last()

    val day = dayState.selectedItem.coerceAtMost(maxDay)

    val newDate = LocalDate(year, month, day)

    onValueChange(newDate)
}