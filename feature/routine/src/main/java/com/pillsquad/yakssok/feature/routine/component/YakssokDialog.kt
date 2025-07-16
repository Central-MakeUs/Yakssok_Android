package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
fun YakssokDialog(
    title: String = "",
    cancelText: String? = null,
    confirmText: String = "선택",
    enabled: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    titleComponent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val confirmColor = if (enabled) YakssokTheme.color.primary400 else YakssokTheme.color.grey200
    val contentColor = if (enabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .background(
                        color = YakssokTheme.color.grey50,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(top = 12.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                Spacer(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(4.dp)
                        .width(38.dp)
                        .background(color = Color(0xFFDBDBDB))
                )
                Spacer(modifier = Modifier.height(28.dp))
                if (titleComponent == null) {
                    Text(
                        text = title,
                        style = YakssokTheme.typography.subtitle1,
                        color = YakssokTheme.color.grey900
                    )
                } else {
                    titleComponent()
                }
                Spacer(modifier = Modifier.height(20.dp))
                content()
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (cancelText != null) {
                        YakssokButton(
                            modifier = Modifier.weight(1f),
                            text = cancelText,
                            backgroundColor = YakssokTheme.color.grey100,
                            onClick = onDismiss
                        )
                    }
                    YakssokButton(
                        modifier = Modifier.weight(1f),
                        text = confirmText,
                        contentColor = contentColor,
                        backgroundColor = confirmColor,
                        enabled = enabled,
                        onClick = onConfirm
                    )
                }
            }
        }
    }
}