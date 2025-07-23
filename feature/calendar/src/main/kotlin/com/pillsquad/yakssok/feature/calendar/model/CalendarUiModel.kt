package com.pillsquad.yakssok.feature.calendar.model

import android.util.SparseArray
import com.pillsquad.yakssok.core.model.Mate
import com.pillsquad.yakssok.core.model.Medicine
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class CalendarUiModel(
    val selectedDate: LocalDate = LocalDate.today(),
    val mateList: List<Mate> = listOf(
        Mate(
            id = 1,
            name = "임용수",
            nickName = "나",
            profileImage = "https://picsum.photos/200",
            remainedMedicine = 1
        ),
        Mate(
            id = 2,
            name = "조앵",
            nickName = "PM",
            profileImage = "https://picsum.photos/200",
            remainedMedicine = 0
        ),
        Mate(
            id = 3,
            name = "리아",
            nickName = "iOS",
            profileImage = "https://picsum.photos/200",
            remainedMedicine = 3
        ),
        Mate(
            id = 4,
            name = "노을",
            nickName = "Server",
            profileImage = "https://picsum.photos/200",
            remainedMedicine = 3
        )
    ),
    val selectedMate: Int = 1,
    // userId to <먹을 날짜, 먹어야 하는 약 리스트>
    val medicineCache: SparseArray<MutableMap<LocalDate, List<Medicine>>> = SparseArray(),
    // userId to <먹을 날짜, 그 날짜에 약 다 먹었는가?>
    val takenCache: SparseArray<MutableMap<LocalDate, Boolean>> = SparseArray()
)

@OptIn(ExperimentalTime::class)
fun LocalDate.Companion.today(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}
