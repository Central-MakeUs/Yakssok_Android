package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.User

interface UserRepository {
    suspend fun postMyInfoToLocal()
    suspend fun getMyInfo(): User
}