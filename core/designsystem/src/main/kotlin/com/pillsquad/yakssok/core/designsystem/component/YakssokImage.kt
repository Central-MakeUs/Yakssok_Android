package com.pillsquad.yakssok.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pillsquad.yakssok.core.designsystem.R
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme

@Composable
fun YakssokImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    isStroke: Boolean = false,
) {
    val shape = CircleShape

    AsyncImage(
        modifier = modifier
            .then(
                if (isStroke) Modifier.border(
                    2.dp,
                    YakssokTheme.color.primary600,
                    shape
                ) else Modifier
            )
            .clip(shape)
            .background(YakssokTheme.color.grey200),
        model = imageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription ?: stringResource(R.string.yakssok_image),
        error = painterResource(R.drawable.img_default_profile)
    )
}