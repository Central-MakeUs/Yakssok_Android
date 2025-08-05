package com.pillsquad.yakssok.core.domain

import com.pillsquad.yakssok.core.domain.repository.AuthRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class TestLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() {
        authRepository.testLoginUser()
        userRepository.postMyInfoToLocal()
    }
}