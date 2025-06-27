package com.pillsquad.yakssok.domain.repository

import com.pillsquad.yakssok.core.model.User

interface UserRepository {
    suspend fun getUserByGymName(gymName: String): Result<User>
}