package com.pillsquad.yakssok.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.DeleteAccountUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetMyInfoUseCase
import com.pillsquad.yakssok.core.domain.usecase.LogoutUserUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostUserDevicesUseCase
import com.pillsquad.yakssok.feature.mypage.model.MyPageUiModel
import com.pillsquad.yakssok.feature.mypage.model.MyPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyPageEvent {
    data class ShowToast(val message: String) : MyPageEvent()
}

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val postUserDevicesUseCase: PostUserDevicesUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow<MyPageUiState>(MyPageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var _event = MutableSharedFlow<MyPageEvent>()
    val event = _event.asSharedFlow()

    private val notificationGranted = MutableStateFlow<Boolean?>(null)
    fun setNotificationPermission(granted: Boolean) {
        notificationGranted.value = granted
    }

    private var lastForcedOffKey: Boolean = false

    init {
        observeMyInfoCombined()
    }

    fun updateAgreement() {
        viewModelScope.launch {
            val cur = (uiState.value as? MyPageUiState.Success)?.data ?: return@launch
            val target = !cur.isAgreement

            _uiState.update { state ->
                if (state is MyPageUiState.Success) {
                    MyPageUiState.Success(state.data.copy(isAgreement = target))
                } else state
            }

            postUserDevicesUseCase(target)
                .onFailure { e ->
                    _uiState.update { state ->
                        if (state is MyPageUiState.Success) {
                            MyPageUiState.Success(state.data.copy(isAgreement = !target))
                        } else state
                    }
                    _event.emit(MyPageEvent.ShowToast("네트워크 환경을 확인해주세요."))
                    e.printStackTrace()
                }
        }
    }

    fun logoutUser() { viewModelScope.launch { logoutUserUseCase() } }

    fun deleteAccount() { viewModelScope.launch { deleteAccountUseCase() } }

    private fun observeMyInfoCombined() {
        viewModelScope.launch {
            val intoFlow = getMyInfoUseCase()
                .map {
                    MyPageUiModel(
                        nickName = it.nickName,
                        profileImageUrl = it.profileImage,
                        medicationCount = it.medicationCount,
                        mateCount = it.followingCount,
                        isAgreement = it.isAgreement
                    )
                }
                .catch { e ->
                    _uiState.value = MyPageUiState.Error(e.message ?: "알 수 없는 오류")
                    _event.emit(MyPageEvent.ShowToast("네트워크 환경을 확인해주세요."))
                }

            combine(
                intoFlow,
                notificationGranted,
            ) { model, grantedOrNull ->
                val granted = grantedOrNull

                val effectiveAgreement = when (granted) {
                    null -> model.isAgreement
                    true -> true
                    false -> false
                }
                Triple(model, granted, effectiveAgreement)
            }.collect { (model, granted, effectiveAgreement) ->
                _uiState.value = MyPageUiState.Success(model.copy(isAgreement = effectiveAgreement))

                val needForceOff = (granted == false && model.isAgreement)
                if (needForceOff && !lastForcedOffKey) {
                    lastForcedOffKey = true
                    viewModelScope.launch {
                        postUserDevicesUseCase(false)
                            .onFailure {
                                _event.emit(MyPageEvent.ShowToast("네트워크 환경을 확인해주세요."))
                                lastForcedOffKey = false
                            }
                    }
                }

                if (granted == true) lastForcedOffKey = false
            }
        }
    }
}