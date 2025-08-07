package com.pillsquad.yakssok.feature.home.model

import android.util.SparseArray
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import kotlinx.datetime.LocalDate

sealed class HomeUiState() {
    data object Loading : HomeUiState()
    data class Success(
        val selectedUserIdx: Int = 0,
        val selectedDate: LocalDate = LocalDate.today(),
        val userList: List<User> = listOf(
            User(
                id = 0,
                nickName = "나",
                relationName = "나",
                profileImage = ""
            )
        ),
        val routineCache: SparseArray<MutableMap<LocalDate, List<Routine>>> = SparseArray(),
        val showFeedBackSection: Boolean = false,
    ) : HomeUiState()
    data class Failure(val throwable: Throwable) : HomeUiState()
}
