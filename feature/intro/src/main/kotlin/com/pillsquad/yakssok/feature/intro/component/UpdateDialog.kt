package com.pillsquad.yakssok.feature.intro.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.intro.R

@Composable
internal fun UpdateDialog(
    type: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    // 2 => network, 3 => error
    val message = when (type) {
        0 -> stringResource(R.string.flexible_update)
        1 -> stringResource(R.string.immediate_update)
        2 -> stringResource(R.string.network_error)
        else -> stringResource(R.string.other_error)
    }

    YakssokDialog(
        title = "업데이트 안내",
        cancelText = "취소",
        confirmText = "확인",
        onDismiss = onDismiss,
        onConfirm = onConfirm
    ) {
        Text(
            text = message,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
    }
}