package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.common.formatLocalTime
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.ui.R
import kotlinx.datetime.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DailyMedicineRow(
    routine: Routine,
    isFeedback: Boolean = false,
    isCheckBoxVisible: Boolean = false,
    onClick: () -> Unit = {}
) {
    val backgroundColor = when {
        isFeedback -> Color.Transparent
        else -> YakssokTheme.color.grey100
    }
    val textColor = when {
        isFeedback -> YakssokTheme.color.grey950
        routine.isTaken -> YakssokTheme.color.grey600
        else -> YakssokTheme.color.grey950
    }
    val rightColor =
        if (routine.isTaken) YakssokTheme.color.primary200 else YakssokTheme.color.grey200

    val checkIcon =
        if (routine.isTaken) R.drawable.ic_checkbox_true else R.drawable.ic_checkbox_false

    val formattedTime = formatLocalTime(routine.intakeTime)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .then(
                if (isFeedback) {
                    Modifier.border(
                        width = 1.dp,
                        color = YakssokTheme.color.grey200,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            ),
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
                    .background(Color(routine.medicationType.color))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = routine.medicationName,
                style = YakssokTheme.typography.subtitle2,
                color = textColor,
                textDecoration = if (routine.isTaken && !isFeedback) TextDecoration.LineThrough else null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier
                    .width(1.dp)
                    .height(10.dp),
                painter = painterResource(R.drawable.ic_divider),
                contentDescription = stringResource(R.string.divider),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = formattedTime,
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey400
            )
        }

        if (!isFeedback) {
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
                if (isCheckBoxVisible) {
                    Icon(
                        painter = painterResource(checkIcon),
                        contentDescription = "check",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}