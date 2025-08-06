package com.pillsquad.yakssok.feature.myroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyRoutineListUseCase
import com.pillsquad.yakssok.core.domain.usecase.PutEndRoutineUseCase
import com.pillsquad.yakssok.core.model.MedicationStatus
import com.pillsquad.yakssok.feature.myroutine.model.PillUiModel
import com.pillsquad.yakssok.feature.myroutine.model.RoutineUiModel
import com.pillsquad.yakssok.feature.myroutine.model.toPillUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MyRoutineEvent {
    data class ShowToast(val message: String) : MyRoutineEvent()
}

@HiltViewModel
class MyRoutineViewModel @Inject constructor(
    private val getMyRoutineListUseCase: GetMyRoutineListUseCase,
    private val putEndRoutineUseCase: PutEndRoutineUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(RoutineUiModel())
    val uiState = _uiState.asStateFlow()

    private var _event = MutableSharedFlow<MyRoutineEvent>()
    val event = _event.asSharedFlow()

    init {
        getMyRoutineList()
    }

    fun checkEndRoutine(id: Int) {
        viewModelScope.launch {
            val isAlreadyEnd = _uiState.value.pillList.any {
                it.id == id && it.medicationStatus == MedicationStatus.ENDED
            }

            if (isAlreadyEnd) {
                updateDialogId(optional = null)
                _event.emit(
                    MyRoutineEvent.ShowToast("복용 완료된 루틴은 종료할 수 없습니다.")
                )
            } else {
                updateDialogId(optional = null, routineEnd = id)
            }
        }
    }

    fun updateDialogId(optional: Int? = null, routineEnd: Int? = null) {
        _uiState.update { it.copy(optionalShowId = optional, routineEndId = routineEnd) }
    }

    fun endRoutine(id: Int) {
        viewModelScope.launch {
            putEndRoutineUseCase(id)
                .onSuccess {
                    _uiState.update { model ->
                        model.copy(
                            pillList = model.pillList.map { pill ->
                                if (pill.id == id) {
                                    pill.copy(medicationStatus = MedicationStatus.ENDED)
                                } else {
                                    pill
                                }
                            }
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                    Log.e("MyRoutineViewModel", "endRoutine: $it")
                }
        }
    }

    fun getMyRoutineList() {
        viewModelScope.launch {
            getMyRoutineListUseCase().onSuccess { response ->
                _uiState.update { model ->
                    model.copy(
                        pillList = response.map { medication -> medication.toPillUiModel() }
                    )
                }
            }.onFailure {
                it.printStackTrace()
                Log.e("MyRoutineViewModel", "getMyRoutineList: $it")
            }
        }
    }
}