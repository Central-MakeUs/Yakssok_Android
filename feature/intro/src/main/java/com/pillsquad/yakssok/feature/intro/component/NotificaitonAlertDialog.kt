package com.pillsquad.yakssok.feature.intro.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog

@Composable
internal fun NotificationAlertDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    YakssokDialog(
        title = "알림 권한 필요",
        cancelText = "거부",
        confirmText = "확인",
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = YakssokTheme.typography.body2.toSpanStyle()
                                .copy(
                                    color = YakssokTheme.color.grey400,
                                    fontWeight = YakssokTheme.typography.subtitle2.fontWeight
                                )
                        ) {
                            append("약쏙")
                        }
                        withStyle(
                            style = YakssokTheme.typography.body2.toSpanStyle()
                                .copy(YakssokTheme.color.grey600)
                        ) {
                            append("에서 알림을 보내는 것을\n허용하시겠습니까?")
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
    )
}

@Composable
internal fun SettingAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    YakssokDialog(
        title = "알림 권한 안내",
        cancelText = "취소",
        confirmText = "확인",
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = YakssokTheme.typography.body2.toSpanStyle()
                                .copy(
                                    color = YakssokTheme.color.grey400,
                                    fontWeight = YakssokTheme.typography.subtitle2.fontWeight
                                )
                        ) {
                            append("약쏙")
                        }
                        withStyle(
                            style = YakssokTheme.typography.body2.toSpanStyle()
                                .copy(YakssokTheme.color.grey600)
                        ) {
                            append("에서 알림 기능을 사용하기 위해서는 알림 권한이 필요합니다. 설정화면에서 알림 권한을 허용해주세요.\n*애플리케이션 정보 > 알림 > 알림 허용")
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
    )
}