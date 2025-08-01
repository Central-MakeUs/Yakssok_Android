package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pillsquad.yakssok.core.common.formatLocalTime
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.ui.R
import com.pillsquad.yakssok.core.ui.component.DailyMedicineRow
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun RemindDialog(
    name: String,
    routineList: List<Routine>,
    onDismiss: () -> Unit,
) {
    YakssokDialog(
        title = "${name}님,\n지금 드셔야 할 약이에요",
        confirmText = "닫기",
        onDismiss = onDismiss,
        onConfirm = onDismiss,
        confirmContentColor = YakssokTheme.color.grey400,
        confirmBackgroundColor = YakssokTheme.color.grey100,
        content = {
            if (routineList.size > 5) {
                val itemHeight = 56.dp
                val maxVisibleItems = 5
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .height(itemHeight * maxVisibleItems + 32.dp)
                        .verticalScroll(scrollState)
                ) {
                    routineList.forEach {
                        RoutineItem(routine = it)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                routineList.forEach {
                    RoutineItem(routine = it)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}

@Composable
private fun RoutineItem(
    routine: Routine
) {
    val formattedTime = formatLocalTime(routine.intakeTime)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
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
                    .background(Color(routine.medicationType.color))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = routine.medicationName,
                style = YakssokTheme.typography.subtitle2,
                color = YakssokTheme.color.grey950,
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
    }
}