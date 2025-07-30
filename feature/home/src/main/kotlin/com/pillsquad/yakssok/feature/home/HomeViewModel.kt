package com.pillsquad.yakssok.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.feature.home.model.HomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileListUseCase: GetUserProfileListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiModel())
    val uiState = _uiState.asStateFlow()

    init {
        getUserProfileList()
    }

    fun onSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(
            selectedDate = date
        )
    }

    fun onMateClick(userId: Int) {
        _uiState.value = _uiState.value.copy(
            selectedUserIdx = userId
        )
    }

    private fun getUserProfileList() {
        viewModelScope.launch {
            getUserProfileListUseCase()
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        userList = it
                    )
                }
                .onFailure {
                    // Todo: Toast
                    Log.e("HomeViewModel", "getUserProfileList: $it")
                }
        }
    }
}