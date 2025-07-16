package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.model.WeekType

@Composable
internal fun IntakeDayDialog(
    selectedDay: List<WeekType>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onValueChange: (List<WeekType>) -> Unit
) {
    var isEveryDay by remember(selectedDay) { mutableStateOf(selectedDay.size == 7) }
    val weekList = WeekType.entries.toList()

    YakssokDialog(
        title = "복용주기를 선택해주세요",
        cancelText = "닫기",
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                CircleRow(
                    items = weekList,
                    selectedItems = selectedDay,
                    onClick = {
                        onValueChange(selectedDay.toMutableList().apply {
                            if (it in selectedDay) {
                                remove(it)
                            } else {
                                add(it)
                            }
                        })
                    },
                    labelMapper = {
                        it.krName
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                RoutineCheckBox(
                    title = "매일",
                    isTrued = isEveryDay,
                    onClick = {
                        if (isEveryDay) {
                            onValueChange(listOf())
                        } else {
                            onValueChange(weekList)
                        }
                    }
                )
            }
        },
        enabled = selectedDay.isNotEmpty(),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}