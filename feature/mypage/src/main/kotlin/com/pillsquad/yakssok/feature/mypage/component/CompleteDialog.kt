package com.pillsquad.yakssok.feature.mypage.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun CompleteDialog(
    isLogout: Boolean,
    onDismiss: () -> Unit
) {
    val title = if (isLogout) "로그아웃 완료" else "회원탈퇴 완료"

    YakssokDialog(
        title = title,
        confirmText = "또 봐요!",
        onDismiss = onDismiss,
        onConfirm = onDismiss
    ) {
        Text(
            text = "다음에 또 봐요 우리 약쏙!",
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey900
        )
    }
}