package com.pillsquad.yakssok.feature.routine.model

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PickerState<T>(
    val lazyListState: LazyListState,
    val initialIndex: Int,
    private var _items: List<T>
) {
    private val _selectedIndex = MutableStateFlow(initialIndex)
    val selectedIndex: StateFlow<Int>
        get() = _selectedIndex

    val items: List<T>
        get() = _items

    val selectedItem: T
        get() = items.getOrElse(_selectedIndex.value) { items.first() }

    fun updateSelectedIndex(newIndex: Int) {
        Log.d("PickerInit", "items: $items")
        Log.d("PickerInit", "updateSelectedIndex: $newIndex")
        _selectedIndex.value = newIndex.coerceIn(0, items.size - 1)
    }

    fun updateItems(newItems: List<T>) {
        _items = newItems
        if (_selectedIndex.value >= _items.size) {
            _selectedIndex.value = (_items.size - 1).coerceAtLeast(0)
        }
    }
}

@Composable
fun <T> rememberPickerState(
    lazyListState: LazyListState = rememberLazyListState(),
    initialIndex: Int = 0,
    items: List<T>
): PickerState<T> = remember { PickerState(lazyListState, initialIndex, items) }