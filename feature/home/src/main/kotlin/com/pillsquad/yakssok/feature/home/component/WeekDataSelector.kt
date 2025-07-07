package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.home.R
import java.time.LocalDate

@Composable
internal fun WeekDataSelector(
    weekDates: List<LocalDate> = emptyList(),
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onNavigateCalendar: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(YakssokTheme.color.grey50)
            .padding(vertical = 8.dp)
    ) {
        CalendarHeader(
            selectedDate = selectedDate,
            onNavigateCalendar = onNavigateCalendar
        )
        Spacer(modifier = Modifier.height(12.dp))
        RowCalendar(
            weekDates = weekDates,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )
    }
}

@Composable
private fun CalendarHeader(
    selectedDate: LocalDate,
    onNavigateCalendar: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${selectedDate.year}년 ${selectedDate.monthValue}월",
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey500
        )

        Row(
            modifier = Modifier.clickable { onNavigateCalendar() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "캘린더",
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey400
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                tint = YakssokTheme.color.grey400
            )
        }
    }
}

@Composable
private fun RowCalendar(
    weekDates: List<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    val dayLabels = listOf("월", "화", "수", "목", "금", "토", "일")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        weekDates.forEachIndexed { idx, date ->
            val isSelected = (date == selectedDate)
            val label = dayLabels[idx]
            val textStyle = if (isSelected) YakssokTheme.typography.subtitle2 else YakssokTheme.typography.body1

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isSelected) YakssokTheme.color.grey900 else Color.Transparent
                    )
                    .clickable { onDateSelected(date) }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.height(24.dp),
                    text = label,
                    style = textStyle,
                    color = if (isSelected) YakssokTheme.color.grey50 else YakssokTheme.color.grey400
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.height(24.dp),
                    text = "${date.dayOfMonth}",
                    style = textStyle,
                    color = if (isSelected) YakssokTheme.color.grey50 else YakssokTheme.color.grey600
                )
            }
        }
    }
}