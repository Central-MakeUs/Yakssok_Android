package com.pillsquad.yakssok.feature.calendar

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.feature.calendar.model.CalendarUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
): ViewModel(){
    private val _uiState = MutableStateFlow(CalendarUiModel())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
    }
}