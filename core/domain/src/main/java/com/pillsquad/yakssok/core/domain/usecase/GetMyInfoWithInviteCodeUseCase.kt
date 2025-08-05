package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class GetMyInfoWithInviteCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Pair<String, String> {
        return userRepository.getMyInviteCode() to userRepository.getMyUser().nickName
    }
}