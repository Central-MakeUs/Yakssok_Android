package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
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
import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.R
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
import com.pillsquad.yakssok.feature.routine.util.formatKotlinxTime
import com.pillsquad.yakssok.feature.routine.util.toBackground

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
            TypeCard(uiState.pillType ?: PillType.OTHER)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = uiState.pillName,
                style = YakssokTheme.typography.subtitle1,
                color = YakssokTheme.color.grey950
            )
            Spacer(modifier = Modifier.height(16.dp))
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

@Composable
private fun TypeCard(
    pillType: PillType
) {
    Row(
        modifier = Modifier
            .background(
                color = pillType.toBackground(),
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(pillType.color))
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = pillType.krName,
            style = YakssokTheme.typography.caption,
            color = Color(pillType.color)
        )
    }
}

@Composable
private fun WeekRow(
    weekList: List<WeekType>
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(weekList) { index, item ->
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .background(
                        color = YakssokTheme.color.grey100,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.krName,
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.grey600
                )
            }

            if (index != weekList.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "·",
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.grey300,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}