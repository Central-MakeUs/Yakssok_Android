package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.common.formatKotlinxTime
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.ui.component.PillTypeCard
import com.pillsquad.yakssok.core.ui.component.WeekRow
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.R
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel

@Composable
internal fun CompleteDialog(
    uiState: RoutineUiModel,
    onConfirm: () -> Unit,
) {
    YakssokDialog(
        confirmText = "완료",
        titleComponent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "복약알림이 등록되었어요!",
                    style = YakssokTheme.typography.subtitle1,
                    color = YakssokTheme.color.grey900
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.img_alleat),
                    contentDescription = "congratuation",
                    tint = Color.Unspecified
                )
            }
        },
        content = {
            InfoCard(uiState)
        },
        enabled = true,
        onDismiss = onConfirm,
        onConfirm = onConfirm
    )
}

@Composable
private fun InfoCard(
    uiState: RoutineUiModel,
) {
    val timeString = uiState.intakeTimes.joinToString(" / ") { formatKotlinxTime(it) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = YakssokTheme.color.grey200,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            PillTypeCard(uiState.medicationType ?: MedicationType.OTHER)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = uiState.pillName,
                style = YakssokTheme.typography.subtitle1,
                color = YakssokTheme.color.grey950
            )
            Spacer(modifier = Modifier.height(8.dp))
            WeekRow(uiState.intakeDays)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(YakssokTheme.color.grey100)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "count icon",
                    tint = YakssokTheme.color.grey600
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "하루에 ${uiState.intakeCount}번",
                    style = YakssokTheme.typography.body1,
                    color = YakssokTheme.color.grey600
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = timeString,
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey600
            )
        }
    }
}