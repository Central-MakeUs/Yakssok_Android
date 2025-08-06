package com.pillsquad.yakssok.feature.myroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyRoutineListUseCase
import com.pillsquad.yakssok.core.domain.usecase.PutEndRoutineUseCase
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.feature.myroutine.model.PillUiModel
import com.pillsquad.yakssok.feature.myroutine.model.toPillUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyRoutineEvent {
    data class ShowToast(val message: String): MyRoutineEvent()
}

@HiltViewModel
class MyRoutineViewModel @Inject constructor(
    private val getMyRoutineListUseCase: GetMyRoutineListUseCase,
    private val putEndRoutineUseCase: PutEndRoutineUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow(listOf<PillUiModel>())
    val uiState = _uiState.asStateFlow()

    private var _event = MutableSharedFlow<MyRoutineEvent>()
    val event = _event.asSharedFlow()

    init {
        getMyRoutineList()
    }

    fun endRoutine(id: Int) {
        viewModelScope.launch {
            val isAlreadyEnd = _uiState.value.any { it.id == id && it.medicationStatus == MedicationStatus.ENDED }
            if(isAlreadyEnd) {
                _event.emit(MyRoutineEvent.ShowToast("복용 완료된 루틴은 종료할 수 없습니다."))
            }

            putEndRoutineUseCase(id)
                .onSuccess {
                    _uiState.value = _uiState.value.map {
                        if (it.id == id) {
                            it.copy(medicationStatus = MedicationStatus.ENDED)
                        } else {
                            it
                        }
                    }
                }.onFailure {
                    it.printStackTrace()
                    Log.e("MyRoutineViewModel", "endRoutine: $it")
                }
        }
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