package com.pillsquad.yakssok.feature.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pillsquad.yakssok.core.domain.usecase.GetAlarmListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val getAlarmListUseCase: GetAlarmListUseCase
): ViewModel() {
    val alarmList = getAlarmListUseCase().cachedIn(viewModelScope)

}