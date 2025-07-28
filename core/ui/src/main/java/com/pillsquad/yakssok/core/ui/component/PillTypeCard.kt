package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.ui.ext.toBackground

@Composable
fun PillTypeCard(
    medicationType: MedicationType
) {
    Row(
        modifier = Modifier
            .background(
                color = medicationType.toBackground(),
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(medicationType.color))
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = medicationType.krName,
            style = YakssokTheme.typography.caption,
            color = Color(medicationType.color)
        )
    }
}