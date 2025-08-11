package com.pillsquad.yakssok.core.domain.usecase

import android.util.Log
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import javax.inject.Inject

class PutUserInitialUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickName: String): Result<Unit> {
        val result = userRepository.putUserInitial(nickName)

        result.onSuccess {
            userRepository.postMyInfoToLocal()
        }.onFailure {
            it.printStackTrace()
            Log.e("UserRepositoryImpl", "invoke: $it")
        }

        return result
    }
}