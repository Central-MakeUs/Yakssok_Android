package com.pillsquad.yakssok.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun joinUser(
        accessToken: String,
        nickName: String,
        pushAgreement: Boolean
    ): Result<Unit>

    suspend fun loginUser(accessToken: String): Result<Boolean>

    fun checkToken(): Flow<Boolean>
}