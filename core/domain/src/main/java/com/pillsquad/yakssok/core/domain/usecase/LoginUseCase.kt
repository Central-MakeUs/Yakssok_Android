package com.pillsquad.yakssok.core.domain.usecase

import android.util.Log
import com.pillsquad.yakssok.core.domain.repository.AuthRepository
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessToken: String): Result<Boolean> {
        val result = authRepository.loginUser(accessToken)

        result.onSuccess {
            userRepository.postMyInfoToLocal()
        }.onFailure {
            it.printStackTrace()
            Log.e("UserRepositoryImpl", "invoke: $it")
        }

        return result
    }
}