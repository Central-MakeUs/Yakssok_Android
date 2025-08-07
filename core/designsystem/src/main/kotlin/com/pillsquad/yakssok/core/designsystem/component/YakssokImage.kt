package com.pillsquad.yakssok.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pillsquad.yakssok.core.designsystem.R
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.designsystem.util.shadow

@Composable
fun YakssokImage(
    modifier: Modifier = Modifier,
    flag: Int = 0,
    imageUrl: String,
    contentDescription: String? = null,
    isStroke: Boolean = false,
) {
    val shape = CircleShape
    val errorProfile = when(flag % 3) {
        0 -> R.drawable.img_default_profile1
        1 -> R.drawable.img_default_profile2
        else -> R.drawable.img_default_profile3
    }

    AsyncImage(
        modifier = modifier
            .clip(shape)
            .background(YakssokTheme.color.grey200)
            .then(
                if (isStroke) Modifier
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(
                            listOf(
                                YakssokTheme.color.primary300,
                                YakssokTheme.color.primary600
                            )
                        ),
                        shape = shape
                    )
                    .shadow(
                        offsetX = 0.dp,
                        offsetY = 2.dp,
                        blur = 4.dp,
                        color = Color.Black.copy(alpha = 0.15f),
                        shape = shape,
                    ) else Modifier
            ),
        model = imageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription ?: stringResource(R.string.yakssok_image),
        error = painterResource(errorProfile)
    )
}