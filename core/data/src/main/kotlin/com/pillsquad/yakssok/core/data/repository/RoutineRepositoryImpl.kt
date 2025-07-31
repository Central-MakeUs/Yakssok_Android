package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toRoutineAndTakenCache
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.network.datasource.RoutineDataSource
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDataSource: RoutineDataSource
) : RoutineRepository {
    override suspend fun getMyRoutine(
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<UserCache> {
        return routineDataSource.getMyRoutine(
            startDate = startDate.toString(),
            endDate = endDate.toString()
        ).toResult(
            transform = { it.toRoutineAndTakenCache() }
        )
    }

    override suspend fun getFriendRoutine(
        startDate: LocalDate,
        endDate: LocalDate,
        friendsId: Int
    ): Result<UserCache> {
        return routineDataSource.getFriendRoutine(
            startDate = startDate.toString(),
            endDate = endDate.toString(),
            friendsId = friendsId
        ).toResult(
            transform = { it.toRoutineAndTakenCache() }
        )
    }

    override suspend fun putTakeRoutine(scheduleId: Int): Result<Unit> {
        return routineDataSource.putTakeRoutine(scheduleId = scheduleId).toResult()
    }
}