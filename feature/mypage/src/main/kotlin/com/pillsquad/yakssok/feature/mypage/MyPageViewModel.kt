package com.pillsquad.yakssok.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.feature.mypage.model.MyPageUiModel
import com.pillsquad.yakssok.feature.mypage.model.MyPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow<MyPageUiState>(MyPageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)

            _uiState.update {
                MyPageUiState.Success(
                    data = MyPageUiModel()
                )
            }
        }
    }
}