package com.pillsquad.yakssok.feature.home

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.feature.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState

}