package com.pillsquad.yakssok.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
fun YakssokTextField(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YakssokTheme.color.grey100,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    maxLine: Int = 1,
    maxLength: Int = 5,
    isShowClear: Boolean = false,
    isShowCounter: Boolean = false,
    isSuffixEnabled: Boolean = false,
    suffixButtonText: String? = null,
    onSuffixButtonClick: (() -> Unit)? = null,
) {
    val isNotEmpty = value.isNotBlank()
    val verticalPadding = if (suffixButtonText != null && onSuffixButtonClick != null) {
        8
    } else {
        16
    }

    BasicTextField(
        modifier = modifier.defaultMinSize(
            minWidth = 80.dp,
            minHeight = 56.dp
        ),
        value = value,
        onValueChange = {
            if (it.length <= maxLength) onValueChange(it)
        },
        maxLines = maxLine,
        textStyle = YakssokTheme.typography.body1.copy(
            color = YakssokTheme.color.grey950
        ),
        cursorBrush = SolidColor(YakssokTheme.color.primary400),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = verticalPadding.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (!isNotEmpty) {
                        hint?.let { h ->
                            Text(
                                text = h,
                                style = YakssokTheme.typography.body1,
                                color = YakssokTheme.color.grey400
                            )
                        }
                    }
                    innerTextField()
                }

                if (isShowCounter) {
                    Text(
                        text = "${value.length}/$maxLength",
                        style = YakssokTheme.typography.body1,
                        color = YakssokTheme.color.grey400
                    )
                }

                if (isShowClear && isNotEmpty) {
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent),
                        onClick = { onValueChange("") }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "clear",
                            tint = YakssokTheme.color.grey500
                        )
                    }
                }

                if (suffixButtonText != null && onSuffixButtonClick != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = suffixButtonText,
                        color = if (isSuffixEnabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400,
                        style = YakssokTheme.typography.body1,
                        modifier = Modifier
                            .background(
                                color = if (isSuffixEnabled) YakssokTheme.color.grey950 else YakssokTheme.color.grey200,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                onClick = onSuffixButtonClick,
                                enabled = isSuffixEnabled
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    )
}