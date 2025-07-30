package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.User
import javax.inject.Inject

class GetUserProfileListUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        val myData = userRepository.getMyInfo()
        val otherData = friendRepository.getFollowingList()

        return otherData.map { list ->
            listOf(myData) + list
        }
    }
}