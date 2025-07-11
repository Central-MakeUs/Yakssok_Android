package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.routine.model.CurveEffect
import com.pillsquad.yakssok.feature.routine.model.PickerSelector
import com.pillsquad.yakssok.feature.routine.model.PickerState
import com.pillsquad.yakssok.feature.routine.model.PickerStyle
import com.pillsquad.yakssok.feature.routine.model.TimePickerDefaults
import com.pillsquad.yakssok.feature.routine.model.rememberPickerState
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun TimePicker(
    modifier: Modifier = Modifier,
    initialTime: LocalTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).time,
    visibleItemsCount: Int = TimePickerDefaults.VISIBLE_ITEM_COUNT,
    style: PickerStyle = TimePickerDefaults.pickerStyle(),
    selector: PickerSelector = TimePickerDefaults.pickerSelector(),
    curveEffect: CurveEffect = TimePickerDefaults.curveEffect(),
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
                items = amPmItems,
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
                    items = hourItems,
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
                    items = minuteItems,
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

@Composable
private fun SelectorBackground(
    modifier: Modifier = Modifier,
    style: PickerStyle,
    selector: PickerSelector
) {
    val lineHeight = with(LocalDensity.current) { style.textStyle.lineHeight.toDp() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight + 16.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = selector.color
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = selector.color
        )
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

    val adjustedHour = when (amPm) {
        "오전" -> if (hour == 12) 0 else hour
        "오후" -> if (hour != 12) hour + 12 else 12
        else -> hour
    }

    val newTime = LocalTime(adjustedHour, minute)

    onValueChange(newTime)
}