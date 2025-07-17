package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun IntakeCountDialog(
    selectedCount: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onValueChange: (Int) -> Unit
) {
    val countList = listOf(1, 2, 3)

    YakssokDialog(
        title = "하루에 먹을 횟수를 선택해주세요",
        cancelText = "닫기",
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                CircleRow(
                    items = countList,
                    selectedItems = listOf(selectedCount),
                    onClick = {
                        onValueChange(it)
                    },
                    labelMapper = {
                        it.toString()
                    }
                )
            }
        },
        enabled = true,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}