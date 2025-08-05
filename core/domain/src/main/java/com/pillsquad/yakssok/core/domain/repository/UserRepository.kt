package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserInfo

interface UserRepository {
    suspend fun postMyInfoToLocal()
    suspend fun getMyUser(): User
    suspend fun getMyInviteCode(): String
    suspend fun getUserInfoByInviteCode(inviteCode: String): Result<UserInfo>
}