package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.WeekType

@Composable
fun WeekRow(
    weekList: List<WeekType>
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(weekList) { index, item ->
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .background(
                        color = YakssokTheme.color.grey100,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.krName,
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.grey600
                )
            }

            if (index != weekList.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "·",
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.grey300,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}