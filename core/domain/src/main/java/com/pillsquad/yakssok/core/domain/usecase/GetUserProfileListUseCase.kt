package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class GetUserProfileListUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> = supervisorScope {
        val myDef = async { runCatching { userRepository.getMyUser() } }
        val followingDef = async { friendRepository.getFollowingList() }

        val me = myDef.await().getOrElse { return@supervisorScope Result.failure(it) }
        val others = followingDef.await().getOrElse { return@supervisorScope Result.failure(it) }

        val merged = buildList {
            add(me)
            addAll(others.filterNot { it.id == me.id })
        }

        Result.success(merged)
    }
}