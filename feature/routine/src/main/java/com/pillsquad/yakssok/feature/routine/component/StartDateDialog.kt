package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.picker.DatePicker
import kotlinx.datetime.LocalDate

@Composable
internal fun StartDateDialog(
    uiStartDate: LocalDate,
    uiEndDate: LocalDate?,
    today: LocalDate = LocalDate.today(),
    selectedStartDate: LocalDate?,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    onValueChange: (LocalDate?) -> Unit
) {
    val candidateStartDate = selectedStartDate ?: uiStartDate
    val isOnOrAfterToday = candidateStartDate >= today
    val isNotAfterEnd = uiEndDate?.let { candidateStartDate <= it } ?: true
    val isConfirmEnabled = isOnOrAfterToday && isNotAfterEnd

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
        enabled = isConfirmEnabled,
        onDismiss = onDismiss,
        onConfirm = { onConfirm(selectedStartDate ?: today) }
    )
}