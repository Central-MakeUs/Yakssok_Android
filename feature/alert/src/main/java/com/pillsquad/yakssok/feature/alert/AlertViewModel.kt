package com.pillsquad.yakssok.feature.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pillsquad.yakssok.core.domain.usecase.GetAlarmListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    getAlarmListUseCase: GetAlarmListUseCase
) : ViewModel() {
    val alarmList = getAlarmListUseCase().cachedIn(viewModelScope)
}