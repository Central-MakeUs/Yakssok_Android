package com.pillsquad.yakssok.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.widget.SyncWidgetSnapshotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val syncWidgetSnapshotUseCase: SyncWidgetSnapshotUseCase
) : ViewModel(){
    fun syncWidget() {
        viewModelScope.launch {
            syncWidgetSnapshotUseCase()
        }
    }
}