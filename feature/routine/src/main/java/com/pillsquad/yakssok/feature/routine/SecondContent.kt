package com.pillsquad.yakssok.feature.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.feature.routine.component.RoutineText
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

@Composable
internal fun SecondContent(
    startDate: LocalDate,
    endDate: LocalDate?,
    intakeCount: Int,
    intakeDays: List<WeekType>,
    intakeTimes: List<LocalTime>,
    onStartTimeChange: () -> Unit,
    onEndTimeChange: () -> Unit,
    onIntakeCountChange: () -> Unit,
    onIntakeDaysChange: () -> Unit,
    onIntakeTimesChange: (Int) -> Unit // for index
) {
    val formatter = LocalDate.Format {
        year(); char('.'); monthNumber(); char('.'); day()
    }
    val startDateString = startDate.format(formatter)
    val endDateString = endDate?.format(formatter) ?: "종료일 없음"
    val intakeDaysString = if (intakeDays.size == 7) {
        "매일"
    } else {
        intakeDays
            .sortedBy { it.ordinal }
            .joinToString(separator = ",") { it.krName }
    }

    Column(
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        RoutineText(
            firstText = "복용기간",
            secondText = "을 선택해주세요"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextCard(
                modifier = Modifier.weight(1f),
                title = "시작일",
                content = startDateString,
                onClick = onStartTimeChange
            )
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "~",
                style = YakssokTheme.typography.subtitle2,
                color = YakssokTheme.color.grey600
            )
            TextCard(
                modifier = Modifier.weight(1f),
                title = "종료일",
                content = endDateString,
                onClick = onEndTimeChange
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        RoutineText(
            firstText = "요일과 횟수",
            secondText = "를 설정해주세요"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextCard(
                modifier = Modifier.weight(1f),
                title = "매주",
                content = intakeDaysString,
                onClick = onIntakeDaysChange
            )
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "/",
                style = YakssokTheme.typography.subtitle2,
                color = YakssokTheme.color.grey600
            )
            TextCard(
                modifier = Modifier.weight(1f),
                title = "하루에",
                content = "${intakeCount}번",
                onClick = onIntakeCountChange
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(intakeTimes.size) {
                TimeCard(
                    numString = "${it + 1}번째",
                    time = intakeTimes[it],
                    onClick = { onIntakeTimesChange(it) }
                )
            }
        }
    }
}

@Composable
private fun TextCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = YakssokTheme.color.grey50,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = YakssokTheme.typography.caption,
            color = YakssokTheme.color.grey400,
        )
        Text(
            text = content,
            style = YakssokTheme.typography.subtitle2,
            color = YakssokTheme.color.grey950
        )
    }
}

@Composable
private fun TimeCard(
    numString: String,
    time: LocalTime,
    onClick: () -> Unit
) {
    val amPm = if (time.hour >= 12) "오후" else "오전"
    val timeFormatter = LocalTime.Format {
        amPmHour(); char(':'); minute()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = YakssokTheme.color.grey50,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = numString,
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey400
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$amPm ${time.format(timeFormatter)}",
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey700
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "time changer",
                tint = YakssokTheme.color.grey400
            )
        }
    }
}