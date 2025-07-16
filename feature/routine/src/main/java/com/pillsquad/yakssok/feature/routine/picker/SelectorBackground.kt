package com.pillsquad.yakssok.feature.routine.picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun SelectorBackground(
    modifier: Modifier = Modifier,
    style: PickerStyle,
    selector: PickerSelector
) {
    val lineHeight = with(LocalDensity.current) { style.textStyle.lineHeight.toDp() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight + 16.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = selector.color
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = selector.color
        )
    }
}