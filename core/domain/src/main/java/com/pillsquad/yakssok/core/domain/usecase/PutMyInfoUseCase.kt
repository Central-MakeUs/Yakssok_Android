package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class PutMyInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickName: String, profileImage: String) =
        userRepository.putMyInfo(nickName, profileImage)
}