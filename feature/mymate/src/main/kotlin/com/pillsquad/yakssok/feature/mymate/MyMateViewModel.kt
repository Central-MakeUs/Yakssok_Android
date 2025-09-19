package com.pillsquad.yakssok.feature.mymate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMateListUseCase
import com.pillsquad.yakssok.feature.mymate.model.MyMateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMateViewModel @Inject constructor(
    private val getMateListUseCase: GetMateListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MyMateUiState>(MyMateUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMateList()
    }

    fun getMateList() {
        viewModelScope.launch {
            getMateListUseCase().onSuccess {
                if (it.isEmpty()) {
                    _uiState.value = MyMateUiState.Empty
                } else {
                    _uiState.value = MyMateUiState.Success(it)
                }
            }.onFailure {
                it.printStackTrace()
                _uiState.value = MyMateUiState.Failure
            }
        }
    }
}