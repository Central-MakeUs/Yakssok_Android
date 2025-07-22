package com.pillsquad.yakssok.feature.calendar

import android.util.SparseArray
import com.pillsquad.yakssok.core.model.Mate
import com.pillsquad.yakssok.core.model.Medicine
import kotlinx.datetime.LocalDate

data class CalendarUiModel(
    val selectedDate: LocalDate,
    val mateList: List<Mate>,
    val selectedMate: Int,
    val medicineCache: SparseArray<MutableMap<LocalDate, List<Medicine>>>
)
