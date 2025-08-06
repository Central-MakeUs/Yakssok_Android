package com.pillsquad.yakssok.feature.myroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyRoutineListUseCase
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.feature.myroutine.model.PillUiModel
import com.pillsquad.yakssok.feature.myroutine.model.toPillUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRoutineViewModel @Inject constructor(
    private val getMyRoutineListUseCase: GetMyRoutineListUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(listOf<PillUiModel>())
    val uiState = _uiState.asStateFlow()

    init {
        getMyRoutineList()
    }

    private fun getMyRoutineList() {
        viewModelScope.launch {
            getMyRoutineListUseCase().onSuccess {
                _uiState.value = it.map { response -> response.toPillUiModel() }
            }.onFailure {
                it.printStackTrace()
                Log.e("MyRoutineViewModel", "getMyRoutineList: $it")
            }
        }
    }
}