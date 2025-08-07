package com.pillsquad.yakssok.feature.calendar.model

import android.util.SparseArray
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.Routine
import kotlinx.datetime.LocalDate

data class CalendarUiModel(
    val selectedUserIdx: Int = 0,
    val selectedDate: LocalDate = LocalDate.today(),
    val userList: List<User> = listOf(),
    // userId to <먹을 날짜, 먹어야 하는 약 리스트>
    val routineCache: SparseArray<MutableMap<LocalDate, List<Routine>>> = SparseArray(),
    // userId to <먹을 날짜, 그 날짜에 약 다 먹었는가?>
    val takenCache: SparseArray<MutableMap<LocalDate, Boolean>> = SparseArray()
)