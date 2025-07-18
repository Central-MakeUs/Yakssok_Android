package com.pillsquad.yakssok.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
internal fun ProfileRow(
    imgUrl: String,
    name: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        YakssokImage(
            imageUrl = imgUrl,
            contentDescription = "profile image",
            modifier = Modifier.size(52.dp)
        )
        Text(
            text = name,
            style = YakssokTheme.typography.header2,
            color = YakssokTheme.color.grey900
        )
    }
}