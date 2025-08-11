package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun postMyInfoToLocal()
    suspend fun getMyUser(): User
    suspend fun putMyInfo(nickName: String, profileImage: String): Result<Unit>
    suspend fun putUserInitial(nickName: String): Result<Unit>
    suspend fun getMyInviteCode(): String
    suspend fun getUserInfoByInviteCode(inviteCode: String): Result<UserInfo>
    suspend fun getMyInfo(): Flow<MyInfo>
    suspend fun deleteAccount(): Result<Unit>
}