package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Medicine
import com.pillsquad.yakssok.core.ui.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DailyMedicineRow(
    medicine: Medicine,
    onClick: () -> Unit = {}
) {
    val textColor = if (medicine.isTaken) YakssokTheme.color.grey600 else YakssokTheme.color.grey950
    val rightColor =
        if (medicine.isTaken) YakssokTheme.color.grey200 else YakssokTheme.color.primary400
    val checkIcon =
        if (medicine.isTaken) R.drawable.ic_checkbox_false else R.drawable.ic_checkbox_true
    val formattedTime = medicine.time.format(
        DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    )
    val rowHeight = if (medicine.name.length > 10) 72.dp else 56.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)
            .clip(RoundedCornerShape(16.dp))
            .background(YakssokTheme.color.grey100),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(YakssokTheme.color.subPurple)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = medicine.name,
                style = YakssokTheme.typography.subtitle2,
                color = textColor,
                textDecoration = if(medicine.isTaken) TextDecoration.LineThrough else null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier
                    .width(1.dp)
                    .height(10.dp),
                painter = painterResource(R.drawable.ic_divider),
                contentDescription = "divider",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = formattedTime,
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey400
            )
        }

        Box(
            modifier = Modifier
                .width(64.dp)
                .fillMaxHeight()
                .background(rightColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
                .padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(checkIcon),
                contentDescription = "check",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
fun DailyMedicineRowPreview() {
    YakssokTheme {
        Column(
            modifier = Modifier.systemBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DailyMedicineRow(
                medicine = Medicine(
                    name = "종합 비타민 오쏘몰",
                    time = LocalTime.now(),
                    isTaken = true
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            DailyMedicineRow(
                medicine = Medicine(
                    name = "피부약",
                    time = LocalTime.now(),
                    isTaken = false
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            DailyMedicineRow(
                medicine = Medicine(
                    name = "현대백화점에서산알약입니다이오",
                    time = LocalTime.now(),
                    isTaken = false
                )
            )
        }
    }
}