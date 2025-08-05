package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.User
import javax.inject.Inject

class GetMyUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User = userRepository.getMyUser()
}