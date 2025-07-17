package com.pillsquad.yakssok.feature.mate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault

@Composable
internal fun PlusMateScreen(
    name: String,
    nickName: String,
    imgUrl: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileColumn(
            name = name,
            imgUrl = imgUrl
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "위 약쏙 메이트와\n어떤 관계인가요?",
            style = YakssokTheme.typography.header2,
            color = YakssokTheme.color.grey950
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "내가 이 사람을 칭하고 싶은 말을 적어주세요!",
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey600
        )

        Spacer(modifier = Modifier.height(60.dp))

        YakssokTextField(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = YakssokTheme.color.grey50,
            value = nickName,
            onValueChange = onValueChange,
            hint = "내 친구, 울엄마 등",
            isShowClear = true,
            isShowCounter = true,
        )

        Spacer(modifier = Modifier.weight(1f))

        YakssokButton(
            modifier = Modifier.fillMaxWidth(),
            text = "메이트 추가",
            enabled = enabled,
            backgroundColor = if (enabled) YakssokTheme.color.primary400 else YakssokTheme.color.grey200,
            contentColor = if (enabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400,
            onClick = onNavigateHome,
        )
    }
}

@Composable
private fun ProfileColumn(
    name: String,
    imgUrl: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        YakssokImage(
            modifier = Modifier.size(64.dp),
            imageUrl = imgUrl,
            contentDescription = "profile"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = name,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
    }
}