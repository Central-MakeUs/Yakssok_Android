package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.UserCache
import kotlinx.datetime.LocalDate

interface RoutineRepository {
    suspend fun getMyRoutine(startDate: LocalDate, endDate: LocalDate): Result<UserCache>
    suspend fun getFriendRoutine(
        startDate: LocalDate,
        endDate: LocalDate,
        friendsId: Int
    ): Result<UserCache>

    suspend fun putTakeRoutine(scheduleId: Int): Result<Unit>
}