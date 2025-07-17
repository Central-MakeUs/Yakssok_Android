package com.pillsquad.yakssok.feature.mate.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.feature.mate.R

@Composable
internal fun MateCompleteDialog(
    mateName: String,
    mateNickName: String,
    imgUrl: String,
    onNavigateBack: () -> Unit
) {
    YakssokDialog(
        confirmText = "홈으로",
        titleComponent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${mateName}님과 메이트가 되었어요!",
                    style = YakssokTheme.typography.subtitle1,
                    color = YakssokTheme.color.grey900
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.img_alleat),
                    contentDescription = "congratuation",
                    tint = Color.Unspecified
                )
            }
        },
        content = {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                YakssokImage(
                    modifier = Modifier.size(64.dp),
                    imageUrl = imgUrl,
                    contentDescription = "profile"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = YakssokTheme.typography.body2.toSpanStyle()
                                .copy(YakssokTheme.color.grey400)
                        ) {
                            append("${mateNickName}\n")
                        }
                        withStyle(
                            style = YakssokTheme.typography.body2.toSpanStyle()
                                .copy(YakssokTheme.color.grey600)
                        ) {
                            append(mateName)
                        }
                    },
                    textAlign = TextAlign.Center,
                    color = YakssokTheme.color.grey950,
                )
            }
        },
        enabled = true,
        onDismiss = onNavigateBack,
        onConfirm = onNavigateBack
    )
}