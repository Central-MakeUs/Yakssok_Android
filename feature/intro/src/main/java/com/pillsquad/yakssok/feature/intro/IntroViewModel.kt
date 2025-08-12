package com.pillsquad.yakssok.feature.intro

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.pillsquad.yakssok.core.domain.usecase.TestLoginUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetTokenFlowUseCase
import com.pillsquad.yakssok.core.domain.usecase.LoginUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostUserDevicesUseCase
import com.pillsquad.yakssok.core.domain.usecase.PutUserInitialUseCase
import com.pillsquad.yakssok.feature.intro.model.IntroUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class IntroEvent {
    data object NavigateHome: IntroEvent()
    data object NavigateHomeThenMate: IntroEvent()
    data class ShowToast(val message: String): IntroEvent()
}

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val testLoginUseCase: TestLoginUseCase,
    private val getTokenFlowUseCase: GetTokenFlowUseCase,
    private val putUserInitialUseCase: PutUserInitialUseCase,
    private val postUserDevicesUseCase: PostUserDevicesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(IntroUiModel())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<IntroEvent>()
    val event = _event.asSharedFlow()

    private var launchFromOneLink: Boolean = false
    fun markLaunchedFromOneLink(fromOneLink: Boolean) {
        launchFromOneLink = fromOneLink
    }

    init {
        checkToken()
    }

    fun deleteLoginInfo() {
        _uiState.update {
            it.copy(
                isHaveToSignup = false,
                nickName = "",
                token = "",
                isEnabled = false
            )
        }
    }

    fun changeNickName(nickName: String) {
        _uiState.update {
            it.copy(nickName = nickName, isEnabled = validateNickName(nickName))
        }
    }

    // flag는 tiramisu 이상일 때 false, 아닐 때 true
    fun putUserInitial() {
        viewModelScope.launch {
            putUserInitialUseCase(uiState.value.nickName)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = true, loginSuccess = true) }
                }.onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    fun postPushAgreement(pushAgreement: Boolean) {
        viewModelScope.launch {
            postUserDevicesUseCase(pushAgreement)
                .onSuccess {
                    _event.emit(IntroEvent.NavigateHome)
                    launchFromOneLink = false
                }.onFailure {
                    showToast("네트워크 환경을 확인해주세요.")
                    it.printStackTrace()
                    Log.e("UserRepositoryImpl", "invoke: $it")
                }
        }
    }

    fun handleSignIn(context: Context) {
        val kakaoInstance = UserApiClient.instance

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null || token == null) {
                showToast("카카오 로그인 실패")
            } else {
                loginUser(token.accessToken)
            }
        }

        if (kakaoInstance.isKakaoTalkLoginAvailable(context)) {
            kakaoInstance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패 처리
                if (error != null || token == null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        showToast("카카오 로그인 실패")
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

    fun testLoginUser() {
        viewModelScope.launch {
            testLoginUseCase.invoke()
        }
    }

    private fun loginUser(accessToken: String) {
        viewModelScope.launch {
            if (uiState.value.isHaveToSignup) {
                _uiState.update { it.copy(isLoading = true) }
            }
            val result = loginUseCase(accessToken)
            _uiState.update {
                when {
                    result.isSuccess && result.getOrDefault(true) -> it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        token = accessToken
                    )

                    result.isSuccess && !result.getOrDefault(false) -> it.copy(
                        isLoading = false,
                        isHaveToSignup = true,
                        token = accessToken
                    )

                    else -> {
                        showToast("네트워크 환경을 확인해주세요.")
                        it.copy(isLoading = false)
                    }
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
            delay(1000)

            getTokenFlowUseCase().collect { valid ->
                _uiState.update {
                    if (valid) it.copy(isLoading = true, loginSuccess = true, token = "token")
                    else it.copy(isLoading = false)
                }
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _event.emit(IntroEvent.ShowToast(message))
        }
    }
}