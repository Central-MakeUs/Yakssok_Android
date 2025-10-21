package com.pillsquad.yakssok.feature.intro.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun TestAccountDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    var value by remember { mutableStateOf("") }

    YakssokDialog(
        title = "테스트 계정을 위한 다이얼로그입니다.",
        cancelText = "취소",
        confirmText = "로그인",
        enabled = (value == "yakssokTest"),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    ) {
        YakssokTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { value = it },
            hint = "테스트 계정",
            maxLine = 1,
            maxLength = 15
        )
    }
}