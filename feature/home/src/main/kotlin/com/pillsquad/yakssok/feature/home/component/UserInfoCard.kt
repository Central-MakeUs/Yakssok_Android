package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.designsystem.util.shadow
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.FeedbackType
import com.pillsquad.yakssok.feature.home.R

@Composable
internal fun UserInfoCard(
    feedback: FeedbackTarget,
    onClick: () -> Unit
) {
    val isNagging = feedback.feedbackType == FeedbackType.NAG

    Column(
        modifier = Modifier
            .shadow(
                offsetX = 0.dp,
                offsetY = 4.dp,
                blur = 12.dp,
                color = Color.Black.copy(alpha = 0.15f),
            )
            .clip(RoundedCornerShape(16.dp))
            .background(YakssokTheme.color.grey50)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YakssokImage(
            flag = feedback.userId,
            modifier = Modifier.size(52.dp),
            imageUrl = feedback.profileImageUrl,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = feedback.nickName,
            style = YakssokTheme.typography.subtitle2,
            color = YakssokTheme.color.grey600
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isNagging) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = YakssokTheme.color.grey500)) {
                            append("안먹은 약ㆍ")
                        }
                        withStyle(style = SpanStyle(color = YakssokTheme.color.grey900)) {
                            append("${feedback.routineCount}개")
                        }
                    },
                    style = YakssokTheme.typography.body2,
                )
            } else {
                Text(
                    text = "다먹었어요!",
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.grey500
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier.size(21.dp),
                    painter = painterResource(R.drawable.img_alleat),
                    contentDescription = "congratuation",
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (isNagging) {
            YakssokButton(
                modifier = Modifier.height(40.dp),
                text = "잔소리 보내기",
                round = 8.dp,
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                backgroundColor = YakssokTheme.color.grey900,
                contentColor = YakssokTheme.color.grey50,
                onClick = onClick
            )
        } else {
            YakssokButton(
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp),
                text = "칭찬 보내기",
                round = 8.dp,
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                backgroundColor = YakssokTheme.color.subBlue,
                contentColor = YakssokTheme.color.grey50,
                onClick = onClick
            )
        }
    }
}