package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.feature.routine.picker.TimePicker
import com.pillsquad.yakssok.feature.routine.util.now
import kotlinx.datetime.LocalTime

@Composable
internal fun TimeDialog(
    selectedIdx: Int,
    selectedTime: LocalTime?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onValueChange: (LocalTime) -> Unit
) {
    YakssokDialog(
        title = "${selectedIdx + 1}번째 알림받을 시간을 설정해주세요",
        content = {
            TimePicker (
                modifier = Modifier.fillMaxWidth(),
                initialTime = selectedTime ?: LocalTime.now(),
                onValueChange = { onValueChange(it) }
            )
        },
        enabled = true,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}