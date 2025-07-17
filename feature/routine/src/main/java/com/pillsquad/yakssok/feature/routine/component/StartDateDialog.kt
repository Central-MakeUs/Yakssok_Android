package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.picker.DatePicker
import com.pillsquad.yakssok.feature.routine.util.today
import kotlinx.datetime.LocalDate

@Composable
internal fun StartDateDialog(
    uiStartDate: LocalDate,
    today: LocalDate = LocalDate.today(),
    selectedStartDate: LocalDate?,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    onValueChange: (LocalDate?) -> Unit
) {
    YakssokDialog(
        title = "복용 시작 날짜를 설정해주세요",
        cancelText = "닫기",
        content = {
            DatePicker(
                modifier = Modifier.fillMaxWidth(),
                initialDate = selectedStartDate ?: uiStartDate,
                onValueChange = { onValueChange(it) }
            )
        },
        enabled = selectedStartDate?.let {
            it >= today
        } ?: false,
        onDismiss = onDismiss,
        onConfirm = { onConfirm(selectedStartDate ?: today) }
    )
}