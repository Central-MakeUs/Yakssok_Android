package com.pillsquad.yakssok.feature.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.feature.routine.component.RoutineText

@Composable
internal fun ThirdContent(
    selectedAlarmType: AlarmType,
    onAlarmTypeChange: (AlarmType) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        RoutineText(
            firstText = "받고 싶은 알람음",
            secondText = "을 선택해주세요"
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AlarmType.entries) {
                AlarmItem(
                    alarmType = it,
                    isSelected = it == selectedAlarmType,
                    onClick = { onAlarmTypeChange(it) }
                )
            }
        }
    }
}

@Composable
private fun AlarmItem(
    alarmType: AlarmType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconResource = if (isSelected) R.drawable.ic_equalizer else R.drawable.ic_equlizer_off
    val textColor = if (isSelected) YakssokTheme.color.primary400 else YakssokTheme.color.grey900
    val borderModifier = if (isSelected) {
        Modifier
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(YakssokTheme.color.primary500, YakssokTheme.color.primary300),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                ),
                shape = RoundedCornerShape(16.dp)
            )
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(borderModifier)
            .background(
                color = YakssokTheme.color.grey50,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(iconResource),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = alarmType.krName,
            style = YakssokTheme.typography.body1,
            color = textColor
        )
    }
}