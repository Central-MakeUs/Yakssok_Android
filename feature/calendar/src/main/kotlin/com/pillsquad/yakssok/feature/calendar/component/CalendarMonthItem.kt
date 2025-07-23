package com.pillsquad.yakssok.feature.calendar.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.calendar.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

@Composable
internal fun CalendarMonthItem(
    today: LocalDate,
    currentYearMonth: YearMonth,
    selectedDate: LocalDate,
    takenCache: Map<LocalDate, Boolean>,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfWeek = currentYearMonth.firstDay.dayOfWeek.ordinal // 0(일) ~ 6(토)
    val daysInMonth = currentYearMonth.numberOfDays
    val paddedDates = buildList {
        // 앞쪽 공백 (첫 주 요일 위치 맞추기)
        repeat(firstDayOfWeek) {
            add(null)
        }
        // 날짜 채우기
        for (day in 1..daysInMonth) {
            add(LocalDate(currentYearMonth.year, currentYearMonth.month, day))
        }
        // 뒷쪽 공백 (마지막 주 7칸 채우기)
        while (size % 7 != 0) {
            add(null)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Week()

        paddedDates.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (date == null) {
                            Spacer(modifier = Modifier)
                        } else {
                            CalendarDay(
                                date = date,
                                today = today,
                                isSelected = date == selectedDate,
                                isAllTaken = takenCache[date] ?: false,
                                onDateSelected = onDateSelected
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    today: LocalDate,
    isSelected: Boolean,
    isAllTaken: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val isToday = date == today
    val isFuture = date >= today

    val textColor = if (isSelected) {
        YakssokTheme.color.grey50
    } else {
        if (isToday) {
            YakssokTheme.color.primary400
        } else {
            YakssokTheme.color.grey500
        }
    }

    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onDateSelected(date) }
            )
            .padding(top = 4.dp, bottom = 1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isSelected) {
                        Modifier.background(
                            color = YakssokTheme.color.grey900,
                            shape = CircleShape
                        )
                    } else {
                        Modifier
                    }
                ),
            text = date.day.toString(),
            textAlign = TextAlign.Center,
            style = YakssokTheme.typography.body2,
            color = textColor
        )

        Spacer(modifier = Modifier.height(1.dp))

        if (isFuture) {
            Box(
                modifier = Modifier
                    .alpha(0.9f)
                    .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(
                        color = YakssokTheme.color.grey100,
                        shape = CircleShape
                    )
            )
        } else {
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 5.dp),
                painter = painterResource(resourceMatcher(isAllTaken, date.day)),
                contentDescription = "day icon",
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
private fun Week(
    modifier: Modifier = Modifier
) {
    val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        weekDays.forEach {
            Text(
                modifier = Modifier.weight(1f),
                text = it,
                textAlign = TextAlign.Center,
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey400
            )
        }
    }
}

private fun resourceMatcher(
    isAllTaken: Boolean,
    day: Int
): Int {
    return if (isAllTaken) {
        when (day % 6) {
            0 -> R.drawable.ic_success_1
            1 -> R.drawable.ic_success_2
            2 -> R.drawable.ic_success_3
            3 -> R.drawable.ic_success_4
            4 -> R.drawable.ic_success_5
            5 -> R.drawable.ic_success_6
            else -> throw IllegalArgumentException("Invalid day: $day")
        }
    } else {
        when (day % 6) {
            0 -> R.drawable.ic_failure_1
            1 -> R.drawable.ic_failure_2
            2 -> R.drawable.ic_failure_3
            3 -> R.drawable.ic_failure_4
            4 -> R.drawable.ic_failure_5
            5 -> R.drawable.ic_failure_6
            else -> throw IllegalArgumentException("Invalid day: $day")
        }
    }
}

