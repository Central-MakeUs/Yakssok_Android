package com.pillsquad.yakssok.feature.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault

@Composable
internal fun SignupScreen(
    nickName: String = "",
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = false,
    onBackClick: () -> Unit = {},
    onSignupClick: () -> Unit = { }
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100),
    ) {
        YakssokTopAppBar(onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        TitleText()
        Spacer(modifier = Modifier.height(60.dp))
        YakssokTextField(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = YakssokTheme.color.grey50,
            value = nickName,
            onValueChange = onValueChange,
            hint = stringResource(R.string.signup_hint),
            isShowClear = true,
            isShowCounter = true,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            YakssokButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                text = stringResource(R.string.signup),
                enabled = enabled,
                backgroundColor = if (enabled) YakssokTheme.color.primary400 else YakssokTheme.color.grey200,
                contentColor = if (enabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400,
                onClick = onSignupClick,
            )
        }
    }
}

@Composable
private fun TitleText() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = YakssokTheme.color.grey950)) {
                append(stringResource(R.string.signup_title_header))
            }
            withStyle(style = SpanStyle(color = YakssokTheme.color.primary400)) {
                append(stringResource(R.string.signup_title_body))
            }
            withStyle(style = SpanStyle(color = YakssokTheme.color.grey950)) {
                append(stringResource(R.string.signup_title_tail))
            }
        },
        style = YakssokTheme.typography.header2
    )
}