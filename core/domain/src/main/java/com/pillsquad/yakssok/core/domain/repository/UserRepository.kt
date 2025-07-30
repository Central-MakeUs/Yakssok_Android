package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.User

interface UserRepository {
    suspend fun postMyInfoToLocal(): Result<MyInfo>
    suspend fun getMyInfo(): User
}