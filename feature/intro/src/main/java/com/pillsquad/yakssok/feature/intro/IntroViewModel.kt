package com.pillsquad.yakssok.feature.intro

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class IntroViewModel @Inject constructor(

) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {

        }
    }


}