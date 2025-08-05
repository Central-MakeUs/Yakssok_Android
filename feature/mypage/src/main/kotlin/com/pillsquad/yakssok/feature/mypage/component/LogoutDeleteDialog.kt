package com.pillsquad.yakssok.feature.mypage.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun LogoutDeleteDialog(
    isLogout: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val title = if (isLogout) "로그아웃 하시겠습니까?" else "정말 탈퇴하시겠습니까?"
    val confirmText = if (isLogout) "로그아웃" else "탈퇴"

    YakssokDialog(
        title = title,
        cancelText = "취소",
        confirmText = confirmText,
        confirmContentColor = YakssokTheme.color.grey500,
        confirmBackgroundColor = YakssokTheme.color.grey100,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    ) {
        if (isLogout) {
            Spacer(modifier = Modifier.height(24.dp))
        } else {
            Text(
                text = "탈퇴하면 그 즉시 계정 정보가 모두 파기됩니다.",
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey900
            )
        }
    }
}