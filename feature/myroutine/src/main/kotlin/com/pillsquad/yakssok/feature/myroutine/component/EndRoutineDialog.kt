package com.pillsquad.yakssok.feature.myroutine.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun EndRoutineDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    YakssokDialog(
        title = "이 복약 루틴을 종료하시겠습니까?",
        cancelText = "취소",
        confirmText = "종료",
        confirmContentColor = YakssokTheme.color.grey500,
        confirmBackgroundColor = YakssokTheme.color.grey100,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    ) {
        Text(
            text = "오늘부터 이 루틴을 진행하지 않게 됩니다.",
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey900
        )
    }
}