package com.pillsquad.yakssok.feature.routine.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    initialTime: LocalTime = LocalTime.now(),
    visibleItemsCount: Int = PickerDefaults.VISIBLE_ITEM_COUNT,
    style: PickerStyle = PickerDefaults.pickerStyle(),
    selector: PickerSelector = PickerDefaults.pickerSelector(),
    curveEffect: CurveEffect = PickerDefaults.curveEffect(),
    onValueChange: (LocalTime) -> Unit
) {
    val amPmItems = remember {
        listOf("오전", "오후")
    }
    val hourItems = remember { (1..12).toList() }
    val minuteItems = remember { (0..59).toList() }

    val amPmPickerState = rememberPickerState(
        initialIndex = if (initialTime.hour < 12) 0 else 1,
        items = amPmItems
    )
    val hourPickerState = rememberPickerState(
        initialIndex = hourItems.indexOf(if (initialTime.hour % 12 == 0) 12 else initialTime.hour % 12),
        items = hourItems
    )
    val minutePickerState = rememberPickerState(
        initialIndex = minuteItems.indexOf(initialTime.minute),
        items = minuteItems
    )

    var previousHour by remember { mutableIntStateOf(initialTime.hour) }
    val scope = rememberCoroutineScope()

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
                state = amPmPickerState,
                visibleItemsCount = visibleItemsCount,
                style = style,
                modifier = Modifier.weight(0.5f),
                infiniteScroll = false,
                curveEffect = curveEffect,
                onValueChange = {
                    onPickerValueChange(
                        amPmPickerState,
                        hourPickerState,
                        minutePickerState,
                        onValueChange
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
                    state = hourPickerState,
                    visibleItemsCount = visibleItemsCount,
                    style = style,
                    infiniteScroll = true,
                    curveEffect = curveEffect,
                    onValueChange = {
                        onPickerValueChange(
                            amPmPickerState,
                            hourPickerState,
                            minutePickerState,
                            onValueChange
                        )
                        scope.launch {
                            val currentHour = hourPickerState.selectedItem
                            val currentIndex =
                                amPmPickerState.lazyListState.firstVisibleItemIndex % amPmItems.size
                            val nextIndex = (currentIndex + 1) % amPmItems.size

                            if ((currentHour == 12 && previousHour == 11) ||
                                (currentHour == 11 && previousHour == 12)
                            ) {
                                amPmPickerState.lazyListState.animateScrollToItem(nextIndex)
                            }
                            previousHour = currentHour
                        }
                    }
                )
                Text(
                    modifier = Modifier.weight(0.3f),
                    text = "시",
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
                    state = minutePickerState,
                    visibleItemsCount = visibleItemsCount,
                    style = style,
                    infiniteScroll = true,
                    itemFormatter = { it.toString().padStart(2, '0') },
                    curveEffect = curveEffect,
                    onValueChange = {
                        onPickerValueChange(
                            amPmPickerState,
                            hourPickerState,
                            minutePickerState,
                            onValueChange
                        )
                    }
                )
                Text(
                    modifier = Modifier.weight(0.3f),
                    text = "분",
                    style = YakssokTheme.typography.body0,
                    color = YakssokTheme.color.grey500,
                )
            }
        }
    }
}

private fun onPickerValueChange(
    amPmState: PickerState<String>,
    hourState: PickerState<Int>,
    minuteState: PickerState<Int>,
    onValueChange: (LocalTime) -> Unit
) {
    val amPm = amPmState.selectedItem
    val hour = hourState.selectedItem
    val minute = minuteState.selectedItem

    val adjustedHour = when {
        amPm == "오전" && hour == 12 -> 0
        amPm == "오후" && hour != 12 -> hour + 12
        else -> hour
    }

    val newTime = LocalTime(adjustedHour, minute)

    onValueChange(newTime)
}