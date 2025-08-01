package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoByInviteCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(inviteCode: String) =
        userRepository.getUserInfoByInviteCode(inviteCode)
}