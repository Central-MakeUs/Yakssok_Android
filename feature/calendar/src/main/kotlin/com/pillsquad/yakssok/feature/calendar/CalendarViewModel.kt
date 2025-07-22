package com.pillsquad.yakssok.feature.calendar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
): ViewModel(){
    private val _uiState = MutableStateFlow(CalendarUiModel())
    val uiState = _uiState.asStateFlow()

}