package com.pillsquad.yakssok.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginUser(accessToken: String): Result<Boolean>

    suspend fun putLogout(): Result<Unit>

    fun checkToken(): Flow<Boolean>
    suspend fun testLoginUser()
}