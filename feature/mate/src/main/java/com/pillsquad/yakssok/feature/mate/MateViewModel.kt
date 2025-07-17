package com.pillsquad.yakssok.feature.mate

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.feature.mate.model.MateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MateViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(MateUiModel())
    val uiState = _uiState.asStateFlow()

    fun updateCurPage(newPage: Int) {
        _uiState.update {
            it.copy(curPage = newPage)
        }
    }

    fun updateInputCode(newCode: String) {
        _uiState.update {
            it.copy(inputCode = newCode)
        }
    }

    fun updateNickName(nickName: String) {
        _uiState.update {
            it.copy(mateNickName = nickName, isEnabled = validateNickName(nickName))
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }
}