package com.pillsquad.yakssok.feature.routine.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun RoutineText(
    firstText: String,
    secondText: String
) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = buildAnnotatedString {
            withStyle(style = YakssokTheme.typography.subtitle2.toSpanStyle()) {
                append(firstText)
            }
            withStyle(style = YakssokTheme.typography.body1.toSpanStyle()) {
                append(secondText)
            }
        },
        color = YakssokTheme.color.grey950,
    )
}