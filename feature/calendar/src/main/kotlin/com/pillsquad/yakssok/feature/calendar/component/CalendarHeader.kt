package com.pillsquad.yakssok.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokIconButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import kotlinx.datetime.YearMonth

@Composable
internal fun CalendarHeader(
    yearMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = yearMonth.year.toString(),
            style = YakssokTheme.typography.subtitle1,
            color = YakssokTheme.color.grey600
        )

        Row(
            modifier = Modifier.background(
                color = YakssokTheme.color.grey100,
                shape = RoundedCornerShape(12.dp)
            )
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                YakssokIconButton(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    onClick = onPreviousMonth
                )
            }

            VerticalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                thickness = 1.dp,
                color = YakssokTheme.color.grey200
            )

            Box(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                YakssokIconButton(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    onClick = onNextMonth
                )
            }
        }
    }
}