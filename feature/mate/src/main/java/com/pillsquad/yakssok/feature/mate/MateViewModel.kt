package com.pillsquad.yakssok.feature.mate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyInfoWithInviteCodeUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserInfoByInviteCodeUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostAddFriendUseCase
import com.pillsquad.yakssok.core.model.HttpException
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
    data object PostSuccess: MateEvent()
    data class ShowToast(val message: String): MateEvent()
}

@HiltViewModel
class MateViewModel @Inject constructor(
    private val getMyInfoWithInviteCodeUseCase: GetMyInfoWithInviteCodeUseCase,
    private val getUserInfoByInviteCodeUseCase: GetUserInfoByInviteCodeUseCase,
    private val postAddFriendUseCase: PostAddFriendUseCase
): ViewModel() {
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
                    _uiState.value = _uiState.value.copy(
                        friendNickName = it.nickName,
                        friendImageUrl = it.profileImageUrl,
                    )

                    updateCurPage(1)
                }.onFailure {
                    it.printStackTrace()
                    Log.e("MateViewModel", "getFriendInfo: ${it.message}")

                    val message = if (it is HttpException && it.code == 3001L) {
                        it.message
                    } else {
                        "네트워크 환경을 확인하세요."
                    }

                    _event.emit(MateEvent.ShowToast(message))
                }
        }
    }

    fun postAddFriend() {
        viewModelScope.launch {
            postAddFriendUseCase(_uiState.value.friendCode, _uiState.value.relationName)
                .onSuccess {
                    _event.emit(MateEvent.PostSuccess)
                }
                .onFailure {
                    _event.emit(MateEvent.ShowToast(it.message ?: "알 수 없는 오류가 발생했습니다."))
                    it.printStackTrace()
                    Log.e("MateViewModel", "postAddFriend: ${it.message}")
                }
        }
    }

    fun updateCurPage(newPage: Int) {
        _uiState.update {
            it.copy(curPage = newPage)
        }
    }

    fun updateInputCode(newCode: String) {
        _uiState.update {
            it.copy(friendCode = newCode)
        }
    }

    fun updateNickName(relationName: String) {
        _uiState.update {
            it.copy(relationName = relationName, isEnabled = validateNickName(relationName))
        }
    }

    private fun getMyInfoWithCode() {
        viewModelScope.launch {
            val (code, name) = getMyInfoWithInviteCodeUseCase()

            _uiState.value = _uiState.value.copy(myCode = code, myName = name)
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }
}