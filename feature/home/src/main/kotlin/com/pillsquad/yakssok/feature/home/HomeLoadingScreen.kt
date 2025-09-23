package com.pillsquad.yakssok.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.designsystem.util.shadow
import com.pillsquad.yakssok.core.ui.R
import com.pillsquad.yakssok.core.ui.component.SkeletonBox
import com.pillsquad.yakssok.core.ui.component.SkeletonCircle
import com.pillsquad.yakssok.core.ui.component.SkeletonLine
import com.pillsquad.yakssok.feature.home.component.WeekDataSelector
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun HomeSkeleton(
    showFeedbackSection: Boolean
) {
    val today = LocalDate.today()
    val weekDates = remember {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        (0..6).map { monday.plus(it, DateTimeUnit.DAY) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(YakssokTheme.color.grey100)
            .verticalScroll(rememberScrollState())
    ) {
        if (showFeedbackSection) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                item { Spacer(Modifier.width(16.dp)) }

                items(3) {

                    Column {

                        SkeletonBox(
                            modifier = Modifier
                                .size(width = 120.dp, height = 176.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(Modifier.height(8.dp))
                    }

                    Spacer(Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .shadow(
                    offsetX = 0.dp,
                    offsetY = 4.dp,
                    blur = 12.dp,
                    color = Color.Black.copy(alpha = 0.15f),
                )
                .clip(
                    RoundedCornerShape(
                        topStart = if (showFeedbackSection) 24.dp else 0.dp,
                        topEnd = if (showFeedbackSection) 24.dp else 0.dp
                    )
                )
                .background(YakssokTheme.color.grey50)
                .padding(
                    top = if (showFeedbackSection) 32.dp else 10.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(5) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SkeletonCircle(52.dp)
                        Spacer(Modifier.height(6.dp))
                        SkeletonLine(width = 40.dp, height = 10.dp)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // 주간 날짜 선택 바
            WeekDataSelector(
                weekDates = weekDates,
                selectedDate = today,
                onDateSelected = { },
                onNavigateCalendar = { }
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.have_to_take),
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.grey600
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            repeat(2) {
                SkeletonBox(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.taked_medicine),
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey600
            )

            Spacer(modifier = Modifier.height(20.dp))

            repeat(2) {
                SkeletonBox(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}