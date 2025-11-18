package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toRoutineAndTakenCache
import com.pillsquad.yakssok.core.data.mock.MockDataSource
import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.network.datasource.RoutineDataSource
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDataSource: RoutineDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val mockDataSource: MockDataSource
) : RoutineRepository {
    override suspend fun getMyRoutine(): Result<UserCache> {
        return routineDataSource.getMyRoutine().toResult { it.toRoutineAndTakenCache() }
    }

    override suspend fun getMyRoutine(
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<UserCache> = executeOrMock(
        apiCall = {
            routineDataSource.getMyRoutine(
                startDate = startDate.toString(),
                endDate = endDate.toString()
            ).toResult { it.toRoutineAndTakenCache() }
        },
        mockCall = { mockDataSource.getRoutine(0) }
    )

    override suspend fun getFriendRoutine(
        startDate: LocalDate,
        endDate: LocalDate,
        friendsId: Int
    ): Result<UserCache> = executeOrMock(
        apiCall = {
            routineDataSource.getFriendRoutine(
                startDate = startDate.toString(),
                endDate = endDate.toString(),
                friendsId = friendsId
            ).toResult{ it.toRoutineAndTakenCache() }
        },
        mockCall = { mockDataSource.getRoutine(friendsId) }
    )

    override suspend fun putTakeRoutine(scheduleId: Int): Result<Unit> {
        return routineDataSource.putTakeRoutine(scheduleId = scheduleId).toResult()
    }

    private suspend fun <T> executeOrMock(
        isMock: suspend () -> Boolean = {
            userLocalDataSource.tutorialCompleteFlow.firstOrNull()?.not() ?: true
        },
        apiCall: suspend () -> Result<T>,
        mockCall: suspend () -> T
    ): Result<T> {
        return if (isMock()) {
            runCatching { mockCall() }
        } else {
            apiCall()
        }
    }
}