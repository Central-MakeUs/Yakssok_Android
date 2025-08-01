package com.pillsquad.yakssok.feature.mate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyInviteCodeUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserInfoByInviteCodeUseCase
import com.pillsquad.yakssok.feature.mate.model.MateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MateViewModel @Inject constructor(
    private val getMyInviteCodeUseCase: GetMyInviteCodeUseCase,
    private val getUserInfoByInviteCodeUseCase: GetUserInfoByInviteCodeUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(MateUiModel())
    val uiState = _uiState.asStateFlow()

    init {
        getMyInviteCode()
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

    private fun getMyInviteCode() {
        viewModelScope.launch {
            val code = getMyInviteCodeUseCase()

            _uiState.value = _uiState.value.copy(myCode = code)
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }
}