package com.pillsquad.yakssok.core.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.ui.BuildConfig
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UI_INTENT : UiIntent, UI_STATE : UiState, SIDE_EFFECT : SideEffect>(
    initialState: UI_STATE
) : ViewModel() {
    private val logTag = javaClass.simpleName

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<UI_STATE> = _uiState.asStateFlow()

    private val _sideEffect: Channel<SIDE_EFFECT> = Channel(Channel.BUFFERED)
    val sideEffect: Flow<SIDE_EFFECT> = _sideEffect.receiveAsFlow()

    protected val currentState: UI_STATE
        get() = _uiState.value

    abstract fun onIntent(intent: UI_INTENT)

    protected fun intent(reduce: UI_STATE.() -> UI_STATE) {
        _uiState.value = currentState.reduce()
    }

    protected fun postSideEffect(vararg effects: SIDE_EFFECT) {
        viewModelScope.launch {
            effects.forEach { effect ->
                _sideEffect.send(effect)
            }
        }
    }

    protected fun <T> T.debugLog(subject: String): T =
        if (BuildConfig.DEBUG) {
            also { Log.d(logTag, "$subject: $this") }
        } else {
            this
        }
}