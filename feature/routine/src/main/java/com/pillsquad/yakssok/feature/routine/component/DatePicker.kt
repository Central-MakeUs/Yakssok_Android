package com.pillsquad.yakssok.feature.routine.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.routine.model.CurveEffect
import com.pillsquad.yakssok.feature.routine.model.PickerDefaults
import com.pillsquad.yakssok.feature.routine.model.PickerSelector
import com.pillsquad.yakssok.feature.routine.model.PickerState
import com.pillsquad.yakssok.feature.routine.model.PickerStyle
import com.pillsquad.yakssok.feature.routine.model.rememberPickerState
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
    initialDate: LocalDate = getTodayDate(),
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

    val dayItems by remember(yearPickerState.selectedIndex, monthPickerState.selectedIndex) {
        derivedStateOf {
            val year = yearPickerState.selectedItem
            val month = monthPickerState.selectedItem
            getDaysInMonth(year, month)
        }
    }

    val dayPickerState = rememberPickerState(
        initialIndex = initialDate.day - 1,
        items = dayItems
    )

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
                items = yearItems,
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
                    items = monthItems,
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
                    items = dayItems,
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


@OptIn(ExperimentalTime::class)
private fun getTodayDate(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
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
    val day = dayState.selectedItem

    val newDate = LocalDate(year, month, day)

    onValueChange(newDate)
}