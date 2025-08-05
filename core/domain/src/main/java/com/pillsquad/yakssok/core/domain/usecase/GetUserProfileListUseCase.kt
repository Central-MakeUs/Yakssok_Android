package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetUserProfileListUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> = coroutineScope {
        val myDataDeferred = async { userRepository.getMyUser() }
        val otherDataDeferred = async { friendRepository.getFollowingList() }
        val feedbackTargetDeferred = async { friendRepository.getFeedbackTargetList() }

        val myData = myDataDeferred.await()
        val otherResult = otherDataDeferred.await()
        val feedbackResult = feedbackTargetDeferred.await()

        otherResult.onFailure {
            return@coroutineScope Result.failure(it)
        }

        feedbackResult.onFailure {
            return@coroutineScope Result.failure(it)
        }

        val feedbackMap = feedbackResult.getOrElse {
            return@coroutineScope Result.failure(it)
        }.associateBy { it.userId }

        val updatedOthers = otherResult.getOrElse {
            return@coroutineScope Result.failure(it)
        }.map { user ->
            val notTakenCount = feedbackMap[user.id]?.notTakenCount
            user.copy(notTakenCount = notTakenCount)
        }

        Result.success(listOf(myData) + updatedOthers)
    }
}