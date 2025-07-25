package com.pillsquad.yakssok.feature.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.feature.intro.model.IntroUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiModel())
    val uiState = _uiState.asStateFlow()

    init {
        checkToken()
    }

    fun deleteLoginInfo() {
        _uiState.update { it.copy(isHaveToSignup = false, nickName = "", isEnabled = false) }
    }

    fun changeNickName(nickName: String) {
        _uiState.update {
            it.copy(nickName = nickName, isEnabled = validateNickName(nickName))
        }
    }

    fun fetchNickName() {
        _uiState.update { it.copy(isLoading = true) }
    }

    fun handleSignIn(context: Context) {
        val kakaoInstance = UserApiClient.instance

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null || token == null) {
                // Snackbar or Toast 띄우기
            } else {
                loginUser(token.accessToken)
            }
        }

        if (kakaoInstance.isKakaoTalkLoginAvailable(context)) {
            kakaoInstance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패 처리
                if (error != null || token == null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        // Snackbar 띄우기 or Toast 띄우기
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else {
                    loginUser(token.accessToken)
                }
            }
        } else {
            kakaoInstance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun loginUser(accessToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = userRepository.loginUser(accessToken)
            _uiState.update {
                when {
                    result.isSuccess && result.getOrDefault(false) -> it.copy(isLoading = false, loginSuccess = true)
                    result.isSuccess && !result.getOrDefault(true) -> it.copy(isLoading = false, isHaveToSignup = true)
                    else -> it.copy(isLoading = false)
                }
            }
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }

    private fun checkToken() {
        viewModelScope.launch {
            userRepository.checkToken().collect { valid ->
                _uiState.update {
                    if (valid) it.copy(isLoading = true, loginSuccess = true)
                    else it.copy(isLoading = false)
                }
            }
        }
    }
}