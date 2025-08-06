package com.pillsquad.yakssok.feature.myroutine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun OptionalDialog(
    onClick: () -> Unit,
    onDismiss: () -> Unit,
) {
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
                .background(Color.Transparent)
                .clickable(
                    onClick = onDismiss,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .clickable(
                        onClick = {},
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onClick,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "종료하기",
                        style = YakssokTheme.typography.subtitle1,
                        color = YakssokTheme.color.grey900
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = YakssokTheme.color.grey400
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}