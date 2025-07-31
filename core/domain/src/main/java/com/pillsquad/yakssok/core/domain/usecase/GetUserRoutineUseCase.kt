package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.RoutineRepository
import com.pillsquad.yakssok.core.model.UserCache
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetUserRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<UserCache> {
        return routineRepository.getMyRoutine(startDate = startDate, endDate = endDate)
    }

    suspend operator fun invoke(
        userId: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<UserCache> {
        return routineRepository.getFriendRoutine(
            startDate = startDate,
            endDate = endDate,
            friendsId = userId
        )
    }
}