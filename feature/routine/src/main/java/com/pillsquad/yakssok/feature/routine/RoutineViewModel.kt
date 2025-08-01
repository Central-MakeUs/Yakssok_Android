package com.pillsquad.yakssok.feature.routine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetMyInfoUseCase
import com.pillsquad.yakssok.core.domain.usecase.sound.InitSoundPoolUseCase
import com.pillsquad.yakssok.core.domain.usecase.sound.PlayAlarmSoundUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostMedicationUseCase
import com.pillsquad.yakssok.core.domain.usecase.sound.ReleaseSoundPoolUseCase
import com.pillsquad.yakssok.core.domain.usecase.sound.StopAlarmSoundUseCase
import com.pillsquad.yakssok.core.model.AlarmType
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.feature.routine.model.RoutineUiModel
import com.pillsquad.yakssok.feature.routine.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import javax.inject.Inject

sealed interface RoutineEvent {
    data object NavigateBack : RoutineEvent
    data class ShowToast(val message: String) : RoutineEvent
}

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val postMedicationUseCase: PostMedicationUseCase,
    private val playAlarmSoundUseCase: PlayAlarmSoundUseCase,
    private val stopAlarmSoundUseCase: StopAlarmSoundUseCase,
    private val releaseSoundPoolUseCase: ReleaseSoundPoolUseCase,
    private val initSoundPoolUseCase: InitSoundPoolUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RoutineUiModel())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RoutineEvent>()
    val event = _event.asSharedFlow()

    init {
        getUserName()
        initSoundPoolUseCase()
    }

    fun updateCurPage(newPage: Int) = updateState { copy(curPage = newPage) }

    fun updateStartDate(date: LocalDate) = updateState { copy(startDate = date) }

    fun updateEndDate(date: LocalDate?) = updateState { copy(endDate = date) }

    fun updateIntakeCount(count: Int) = updateState { copy(intakeCount = count) }

    fun updateIntakeDays(days: List<WeekType>) = updateState { copy(intakeDays = days) }

    fun updateIntakeTime(time: LocalTime, index: Int) = updateState {
        copy(intakeTimes = intakeTimes.mapIndexed { i, t -> if (i == index) time else t })
    }

    fun updateAlarmType(type: AlarmType) {
        playAlarmSoundUseCase(type, uiState.value.alarmType)
        updateState { copy(alarmType = type) }
    }

    fun updatePillName(name: String) {
        _uiState.update { it.copy(pillName = name) }
        updateEnabled()
    }

    fun updatePillType(type: MedicationType) {
        _uiState.update { it.copy(medicationType = type) }
        updateEnabled()
    }

    fun postMedication() {
        val medication = uiState.value.toDomain()
        viewModelScope.launch {
            postMedicationUseCase(medication)
                .onSuccess {
                    stopSoundPool()
                    _event.emit(RoutineEvent.NavigateBack)
                }.onFailure {
                    it.printStackTrace()
                    Log.e("RoutineViewModel", "postMedication: ${it.message}")
                    _event.emit(RoutineEvent.ShowToast("약을 등록하는데 실패했습니다."))
                }
        }
    }

    fun stopSoundPool() {
        viewModelScope.launch {
            stopAlarmSoundUseCase(uiState.value.alarmType)
        }
    }

    private fun updateEnabled() {
        val isFirstEnabled =
            uiState.value.medicationType != null && uiState.value.pillName.trim().isNotEmpty()
        updateState { copy(enabled = listOf(isFirstEnabled, true, true)) }
    }

    private fun getUserName() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(userName = getMyInfoUseCase().nickName)
            }
        }
    }

    private inline fun updateState(update: RoutineUiModel.() -> RoutineUiModel) {
        _uiState.update { it.update() }
    }

    override fun onCleared() {
        super.onCleared()
        releaseSoundPoolUseCase()
    }
}