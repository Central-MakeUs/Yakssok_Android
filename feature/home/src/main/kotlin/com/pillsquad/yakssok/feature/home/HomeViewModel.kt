package com.pillsquad.yakssok.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.domain.usecase.GetUserByGymNameUseCase
import com.pillsquad.yakssok.feature.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserByGymNameUseCase: GetUserByGymNameUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun searchUser(gymName: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getUserByGymNameUseCase(gymName)
                .onSuccess { _uiState.value = HomeUiState.Success(it) }
                .onFailure { _uiState.value = HomeUiState.Failure(it) }
        }
    }

}