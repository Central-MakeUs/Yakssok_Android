package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.routine.picker.DatePicker
import kotlinx.datetime.LocalDate

@Composable
internal fun EndDateDialog(
    uiStartDate: LocalDate,
    today: LocalDate = LocalDate.today(),
    selectedEndDate: LocalDate?,
    onDismiss: () -> Unit,
    onConfirm: (LocalDate?) -> Unit,
    onValueChange: (LocalDate?) -> Unit
) {
    var noEndDate by remember { mutableStateOf(false) }

    YakssokDialog(
        title = "복용 종료 날짜를 설정해주세요",
        cancelText = "닫기",
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                DatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    initialDate = selectedEndDate ?: today,
                    onValueChange = {
                        noEndDate = false
                        onValueChange(it)
                    }
                )
                RoutineCheckBox(
                    title = "종료일 없음",
                    isTrued = noEndDate,
                    onClick = {
                        noEndDate = !noEndDate
                        onValueChange(
                            if (noEndDate) {
                                null
                            } else {
                                today
                            }
                        )
                    }
                )
            }
        },
        enabled = if (noEndDate) {
            true
        } else {
            val startDate = uiStartDate
            val endDate = selectedEndDate
            if (endDate == null) {
                false
            } else {
                endDate >= startDate
            }
        },
        onDismiss = onDismiss,
        onConfirm = {
            val finalEndDate = if (noEndDate) null else selectedEndDate
            onConfirm(finalEndDate)
        }
    )
}