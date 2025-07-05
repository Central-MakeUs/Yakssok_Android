package com.pillsquad.yakssok.feature.intro

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.feature.intro.model.IntroUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class IntroViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiModel())
    val uiState = _uiState.asStateFlow()

    init {
        checkToken()
    }

    fun deleteLoginInfo() {
        _uiState.update { it.copy(isLogin = false, nickName = "", isEnabled = false) }
    }

    fun changeNickName(nickName: String) {
        _uiState.update {
            it.copy(nickName = nickName, isEnabled = validateNickName(nickName))
        }
    }

    fun fetchNickName() {
        _uiState.update { it.copy(isLoading = true) }
    }

    fun handleSignIn() {
        _uiState.update { it.copy(isLogin = true) }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }

    private fun checkToken() {
        viewModelScope.launch {
            delay(1500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}