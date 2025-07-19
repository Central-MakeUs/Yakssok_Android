package com.pillsquad.yakssok.feature.profile_edit

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.feature.profile_edit.model.ProfileEditUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiModel())
    val uiState = _uiState.asStateFlow()

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(name = newName, enabled = validateNickName(newName))
        }
    }

    fun updateImgUrl(newImgUrl: String) {
        _uiState.update {
            it.copy(imgUrl = newImgUrl)
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }
}