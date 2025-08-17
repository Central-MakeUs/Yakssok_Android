package com.pillsquad.yakssok.feature.profile_edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.ChangeImageUrlUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetMyInfoUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostImageUrlUseCase
import com.pillsquad.yakssok.core.domain.usecase.PutImageUrlUseCase
import com.pillsquad.yakssok.core.domain.usecase.PutMyInfoUseCase
import com.pillsquad.yakssok.feature.profile_edit.model.ProfileEditUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileEditEvent {
    data object CompleteEdit : ProfileEditEvent()
    data class ShowToast(val message: String) : ProfileEditEvent()
}

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val putImageUrlUseCase: PutImageUrlUseCase,
    private val postImageUrlUseCase: PostImageUrlUseCase,
    private val putMyInfoUseCase: PutMyInfoUseCase,
    private val changeImageUrlUseCase: ChangeImageUrlUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiModel())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEditEvent>()
    val event = _event.asSharedFlow()

    init {
        getMyInfo()
    }

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(name = newName, enabled = validateNickName(newName))
        }
    }

    fun updateImgUrl(newImgUrl: String) {
        _uiState.update { it.copy(enabled = false) }

        viewModelScope.launch {
            val fileImageUrl = changeImageUrlUseCase(newImgUrl)
            if (fileImageUrl == null) {
                _event.emit(ProfileEditEvent.ShowToast("이미지 업로드에 실패했습니다."))
                _uiState.update { it.copy(enabled = true) }
                return@launch
            }

            val oldImageUrl = uiState.value.imgUrl
            val result = if (oldImageUrl.isEmpty()) {
                postImageUrlUseCase(fileImageUrl)
            } else {
                putImageUrlUseCase(fileImageUrl)
            }

            val enabled = validateNickName(uiState.value.name)

            result.onSuccess { imageUrl ->
                _uiState.update { it.copy(imgUrl = imageUrl, enabled = enabled) }
            }.onFailure { e ->
                Log.e("ProfileEditViewModel", "updateImgUrl: $e")
                _uiState.update { it.copy(enabled = enabled) }
                _event.emit(ProfileEditEvent.ShowToast("이미지 업로드에 실패했습니다."))
            }
        }
    }

    fun completeEdit() {
        viewModelScope.launch {
            putMyInfoUseCase(uiState.value.name, uiState.value.imgUrl)
                .onSuccess {
                    Log.d("ProfileEditViewModel", "completeEdit: $it")
                    _event.emit(ProfileEditEvent.CompleteEdit)
                }.onFailure {
                    Log.e("ProfileEditViewModel", "failure: $it")
                    _event.emit(ProfileEditEvent.ShowToast("네트워크 환경을 확인해주세요."))
                }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase().collect {
                _uiState.update { uiState ->
                    uiState.copy(
                        name = it.nickName,
                        imgUrl = it.profileImage,
                        enabled = validateNickName(it.nickName)
                    )
                }
            }
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }
}