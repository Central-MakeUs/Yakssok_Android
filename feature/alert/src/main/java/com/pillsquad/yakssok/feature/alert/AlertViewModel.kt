package com.pillsquad.yakssok.feature.alert

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.feature.alert.model.MessageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow<List<MessageUiModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

}