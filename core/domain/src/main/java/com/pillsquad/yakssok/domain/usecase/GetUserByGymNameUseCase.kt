package com.pillsquad.yakssok.domain.usecase

import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByGymNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(gymName: String): Result<User> =
        userRepository.getUserByGymName(gymName)
}