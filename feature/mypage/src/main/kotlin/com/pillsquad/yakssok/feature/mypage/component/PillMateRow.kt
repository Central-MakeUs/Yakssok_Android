package com.pillsquad.yakssok.feature.mypage.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.mypage.R

@Composable
internal fun PillMateRow(
    routineCount: Int,
    mateCount: Int,
    onNavigateMyRoutine: () -> Unit,
    onNavigateMyMate: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        PillItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.img_my_routine,
            title = "내 복용약",
            count = routineCount,
            onClick = onNavigateMyRoutine
        )

        DashedVerticalDivider(modifier = Modifier.padding(vertical = (16).dp))

        PillItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.img_my_mate,
            title = "내 메이트",
            count = mateCount,
            onClick = onNavigateMyMate
        )
    }
}

@Composable
private fun PillItem(
    modifier: Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    count: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = YakssokTheme.color.primary400,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${count}개",
                style = YakssokTheme.typography.header1,
                color = YakssokTheme.color.grey50
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = YakssokTheme.typography.body2,
                    color = YakssokTheme.color.primary100
                )
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "arrow right",
                    tint = YakssokTheme.color.primary100
                )
            }
        }

        Icon(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterEnd),
            painter = painterResource(id = iconRes),
            contentDescription = "icon image",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun DashedVerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = YakssokTheme.color.primary400,
    thickness: Dp = 1.dp,
    dashLength: Float = 14f,
    gapLength: Float = 16f
) {
    Canvas(
        modifier = modifier
            .width(thickness)
            .fillMaxHeight()
    ) {
        val stroke = Stroke(
            width = thickness.toPx(),
            pathEffect = dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        )

        drawLine(
            color = color,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            strokeWidth = thickness.toPx(),
            pathEffect = stroke.pathEffect
        )
    }
}