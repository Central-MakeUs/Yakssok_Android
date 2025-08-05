package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import javax.inject.Inject

class PostAddFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(inviteCode: String, relationName: String) =
        friendRepository.postAddFriend(inviteCode, relationName)
}