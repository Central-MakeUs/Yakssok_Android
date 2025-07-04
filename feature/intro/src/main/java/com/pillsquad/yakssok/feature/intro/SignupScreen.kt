package com.pillsquad.yakssok.feature.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun SignupScreen(
    onBackClick: () -> Unit = {},
    onSignupClick: (String) -> Unit = { _ -> }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey100)
            .systemBarsPadding(),
    ) {
        SignupTopAppBar { onBackClick() }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            TitleText()
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignupTopAppBar(
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Transparent),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back"
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