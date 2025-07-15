package com.pillsquad.yakssok.feature.routine.model

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PickerState<T>(
    val lazyListState: LazyListState,
    val initialIndex: Int,
    items: List<T>
) {
    private val _items = mutableStateOf(items)
    val items: List<T> get() = _items.value

    private val _selectedIndex = MutableStateFlow(initialIndex)
    val selectedIndex: StateFlow<Int>
        get() = _selectedIndex

    val selectedItem: T
        get() = items.getOrElse(_selectedIndex.value) { items.first() }

    val suppressScrollSync = mutableStateOf(false)
    val isUserScrolling = mutableStateOf(false)

    fun updateSelectedIndex(newIndex: Int) {
        _selectedIndex.value = newIndex.coerceIn(0, items.size - 1)
    }

    fun updateItems(newItems: List<T>) {
        _items.value = newItems

        if (_selectedIndex.value >= items.size) {
            _selectedIndex.value = (items.size - 1).coerceAtLeast(0)
        }
    }
}

@Composable
fun <T> rememberPickerState(
    lazyListState: LazyListState = rememberLazyListState(),
    initialIndex: Int = 0,
    items: List<T>
): PickerState<T> = remember { PickerState(lazyListState, initialIndex, items) }