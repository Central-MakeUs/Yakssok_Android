package com.pillsquad.yakssok.feature.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
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
                // server에 accessToken 전달
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
                    // 로그인 성공 처리
                    // server에 accessToken 전달
                }
            }
        } else {
            kakaoInstance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun loginUser(accessToken: String) {
        viewModelScope.launch {
            // server에 accessToken 전달
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun validateNickName(nickName: String): Boolean {
        val trimmed = nickName.trim()
        return trimmed.isNotEmpty() && trimmed.length <= 5
    }

    private fun checkToken() {
        viewModelScope.launch {
            // datastore에서 token 존재하는지 확인
            delay(1500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}