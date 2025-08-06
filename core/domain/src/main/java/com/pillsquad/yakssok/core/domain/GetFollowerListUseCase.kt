package com.pillsquad.yakssok.core.domain

import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.model.User
import javax.inject.Inject

class GetFollowerListUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(): Result<List<User>> =
        friendRepository.getFollowerList()
}