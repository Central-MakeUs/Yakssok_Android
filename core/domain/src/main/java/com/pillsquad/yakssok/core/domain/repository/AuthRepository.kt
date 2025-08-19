package com.pillsquad.yakssok.core.domain.repository

interface AuthRepository {

    suspend fun loginUser(accessToken: String): Result<Boolean>

    suspend fun putLogout(): Result<Unit>

    suspend fun checkToken(): Result<Boolean>
    suspend fun testLoginUser(): Result<Unit>
}