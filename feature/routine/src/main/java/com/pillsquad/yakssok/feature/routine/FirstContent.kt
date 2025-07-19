package com.pillsquad.yakssok.feature.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.ui.ext.toBackground
import com.pillsquad.yakssok.feature.routine.component.RoutineText

@Composable
internal fun FirstContent(
    userName: String,
    pillName: String,
    selectedPillType: PillType?,
    onPillNameChange: (String) -> Unit,
    onPillTypeChange: (PillType) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        RoutineText(
            firstText = "${userName}님이 먹을 약",
            secondText = "은 무엇인가요?"
        )
        Spacer(modifier = Modifier.height(20.dp))
        YakssokTextField(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = YakssokTheme.color.grey50,
            value = pillName,
            onValueChange = onPillNameChange,
            hint = "오메가 3, 유산균 등",
            maxLength = 15,
            isShowCounter = true
        )
        Spacer(modifier = Modifier.height(32.dp))
        RoutineText(
            firstText = "약 종류",
            secondText = "를 선택해주세요"
        )
        Spacer(modifier = Modifier.height(20.dp))
        PillTypeRow(
            selectedPillType = selectedPillType,
            onPillTypeChange = onPillTypeChange
        )
    }
}

@Composable
private fun PillTypeRow(
    selectedPillType: PillType?,
    onPillTypeChange: (PillType) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        PillType.entries.forEach { pillType ->
            PillTypeItem(
                pillType = pillType,
                isSelected = selectedPillType == pillType,
                onClick = { onPillTypeChange(pillType) }
            )
        }
    }
}

@Composable
private fun PillTypeItem(
    pillType: PillType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(pillType.color) else YakssokTheme.color.grey200
    val textColor = if (isSelected) Color(pillType.color) else YakssokTheme.color.grey700

    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isSelected) pillType.toBackground() else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(borderColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = pillType.krName,
            style = YakssokTheme.typography.body2,
            color = textColor
        )
    }
}