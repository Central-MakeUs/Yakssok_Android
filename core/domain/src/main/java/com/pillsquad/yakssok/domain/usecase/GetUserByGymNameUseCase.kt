package com.pillsquad.yakssok.domain.usecase

import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.domain.repository.UserRepository

class GetUserByGymNameUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(gymName: String): Result<User> =
        userRepository.getUserByGymName(gymName)
}