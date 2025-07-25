package com.pillsquad.yakssok.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loginUser(accessToken: String): Result<Boolean>

    fun checkToken(): Flow<Boolean>
}