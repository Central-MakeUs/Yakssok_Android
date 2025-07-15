package com.pillsquad.yakssok.feature.routine.component

import android.util.Log
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.pillsquad.yakssok.feature.routine.model.CurveEffect
import com.pillsquad.yakssok.feature.routine.model.PickerState
import com.pillsquad.yakssok.feature.routine.model.PickerStyle
import com.pillsquad.yakssok.feature.routine.model.rememberPickerState
import com.pillsquad.yakssok.feature.routine.util.toPx
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.math.abs

@Composable
internal fun <T> PickerItem(
    modifier: Modifier = Modifier,
    state: PickerState<T>,
    visibleItemsCount: Int,
    style: PickerStyle,
    textModifier: Modifier = Modifier,
    itemFormatter: (T) -> String = { it.toString() },
    infiniteScroll: Boolean,
    curveEffect: CurveEffect,
    onValueChange: (T) -> Unit
) {
    val items = state.items

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = if (infiniteScroll) Int.MAX_VALUE else items.size + visibleItemsMiddle * 2
    val listScrollMiddle = listScrollCount / 2

    val listState = state.lazyListState
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    var itemHeightPixels by remember { mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPixels.toDp() }

    LaunchedEffect(state.initialIndex) {
        val safeStartIndex = state.initialIndex
        val listStartIndex = if (infiniteScroll) {
            getStartIndexForInfiniteScroll(
                itemSize = items.size,
                listScrollMiddle = listScrollMiddle,
                visibleItemsMiddle = visibleItemsMiddle,
                startIndex = safeStartIndex
            )
        } else {
            safeStartIndex
        }
        listState.scrollToItem(listStartIndex, 0)

        if (!infiniteScroll) {
            if (listStartIndex != state.selectedIndex.value) {
                state.updateSelectedIndex(listStartIndex)
            }
            onValueChange(items[listStartIndex])
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        state.isUserScrolling.value = listState.isScrollInProgress
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val centerOffset = layoutInfo.viewportStartOffset +
                        (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2
                layoutInfo.visibleItemsInfo.minByOrNull { item ->
                    val itemCenter = item.offset + (item.size / 2)
                    abs(itemCenter - centerOffset)
                }?.index
            }
            .map { centerIndex ->
                centerIndex?.let { index ->
                    if (infiniteScroll) {
                        index
                    } else {
                        (index - visibleItemsMiddle).coerceIn(0, items.size - 1)
                    }
                }
            }
            .distinctUntilChanged()
            .collect { adjustedIndex ->
                if (state.suppressScrollSync.value) return@collect

                val safeIndex = adjustedIndex?.mod(items.size) ?: return@collect
                if (safeIndex != state.selectedIndex.value) {
                    state.updateSelectedIndex(safeIndex)
                    onValueChange(items[safeIndex])
                }
            }
    }

    val totalItemHeight = itemHeightDp + style.itemSpacing
    val totalItemHeightPx = totalItemHeight.toPx()

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(totalItemHeight * visibleItemsCount)
                .pointerInput(Unit) { detectVerticalDragGestures { change, _ -> change.consume() } }
        ) {
            items(listScrollCount, key = { index -> index }) { index ->
                val layoutInfo by remember { derivedStateOf { listState.layoutInfo } }

                val viewportCenterOffset = layoutInfo.viewportStartOffset +
                        (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

                val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
                val itemCenterOffset = itemInfo?.offset?.let { it + (itemInfo.size / 2) } ?: 0

                val distanceFromCenter = abs(viewportCenterOffset - itemCenterOffset).toFloat()
                val maxDistance = totalItemHeightPx * visibleItemsMiddle

                val alpha = curveEffect.calculateAlpha(distanceFromCenter, maxDistance)
                val scaleY = curveEffect.calculateScaleY(distanceFromCenter, maxDistance)

                val item = getItemForIndex(
                    index = index,
                    items = items,
                    infiniteScroll = infiniteScroll,
                    visibleItemsMiddle = visibleItemsMiddle
                )

                Text(
                    text = item?.let { itemFormatter(it) } ?: "",
                    maxLines = 1,
                    style = style.textStyle,
                    color = style.textColor.copy(alpha = alpha),
                    modifier = Modifier
                        .padding(vertical = style.itemSpacing / 2)
                        .graphicsLayer(scaleY = scaleY)
                        .onSizeChanged { size -> itemHeightPixels = size.height }
                        .then(textModifier)
                )
            }
        }
    }
}

private fun <T> getItemForIndex(
    index: Int,
    items: List<T>,
    infiniteScroll: Boolean,
    visibleItemsMiddle: Int
): T? {
    require(items.isNotEmpty()) { "Items list cannot be empty." }

    return if (!infiniteScroll) {
        items.getOrNull(index - visibleItemsMiddle)
    } else {
        items.getOrNull(index % items.size)
    }
}

fun getStartIndexForInfiniteScroll(
    itemSize: Int,
    listScrollMiddle: Int,
    visibleItemsMiddle: Int,
    startIndex: Int
): Int {
    if (itemSize == 0) {
        return listScrollMiddle - visibleItemsMiddle + startIndex
    }

    return listScrollMiddle - listScrollMiddle % itemSize - visibleItemsMiddle + startIndex
}