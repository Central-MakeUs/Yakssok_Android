package com.pillsquad.yakssok.feature.mymate

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.feature.mymate.model.MyMateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyMateViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(MyMateUiModel())
    val uiState = _uiState.asStateFlow()

}