package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.model.User

interface UserRepository {
    suspend fun searchUser(gymName: String): Result<User>
}