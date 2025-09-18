package com.pillsquad.yakssok.feature.mate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyInfoWithInviteCodeUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserInfoByInviteCodeUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostAddFriendUseCase
import com.pillsquad.yakssok.feature.mate.model.MateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MateEvent {
    data object PostSuccess : MateEvent()
    data class ShowErrorSnackbar(val throwable: Throwable) : MateEvent()
}

@HiltViewModel
class MateViewModel @Inject constructor(
    private val getMyInfoWithInviteCodeUseCase: GetMyInfoWithInviteCodeUseCase,
    private val getUserInfoByInviteCodeUseCase: GetUserInfoByInviteCodeUseCase,
    private val postAddFriendUseCase: PostAddFriendUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(MateUiModel())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<MateEvent>()
    val event = _event.asSharedFlow()

    init {
        getMyInfoWithCode()
    }

    fun getFriendInfo() {
        viewModelScope.launch {
            getUserInfoByInviteCodeUseCase(_uiState.value.friendCode)
                .onSuccess {
                    _uiState.update { model ->
                        model.copy(
                            friendNickName = it.nickName,
                            friendImageUrl = it.profileImageUrl,
                        )
                    }
                    _event.emit(MateEvent.PostSuccess)
                }.onFailure { e ->
                    e.printStackTrace()
                    _event.emit(MateEvent.ShowErrorSnackbar(e))
                }
        }
    }

//    fun postAddFriend() {
//        viewModelScope.launch {
//            postAddFriendUseCase(_uiState.value.friendCode, _uiState.value.relationName)
//                .onSuccess {
//                    _event.emit(MateEvent.PostSuccess)
//                }
//                .onFailure { throwable ->
//                    _event.emit(MateEvent.ShowErrorSnackbar(throwable))
//                    throwable.printStackTrace()
//                }
//        }
//    }

    fun updateInputCode(newCode: String) {
        _uiState.update {
            it.copy(friendCode = newCode)
        }
    }

//    fun updateNickName(relationName: String) {
//        _uiState.update {
//            it.copy(relationName = relationName, isEnabled = validateNickName(relationName))
//        }
//    }

    private fun getMyInfoWithCode() {
        viewModelScope.launch {
            val (code, name) = getMyInfoWithInviteCodeUseCase()

            _uiState.value = _uiState.value.copy(myCode = code, myName = name)
        }
    }

//    private fun validateNickName(nickName: String): Boolean {
//        val trimmed = nickName.trim()
//        return trimmed.isNotEmpty() && trimmed.length <= 5
//    }
}