package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class PutUserInitialUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickName: String): Result<Unit> = runCatching {
        userRepository.putUserInitial(nickName).getOrThrow()
        userRepository.postMyInfoToLocal().getOrThrow()
    }
}