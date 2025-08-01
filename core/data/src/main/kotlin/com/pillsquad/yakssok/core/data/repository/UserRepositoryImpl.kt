package com.pillsquad.yakssok.core.data.repository

import android.util.Log
import com.pillsquad.yakssok.core.data.mapper.toMyInfo
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUserInfo
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserInfo
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.fold

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userRetrofitDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postMyInfoToLocal() {
        val result = userRetrofitDataSource.getMyInfo().toResult(
            transform = { it.toMyInfo() }
        )

        result.onSuccess {
            userLocalDataSource.saveUserName(it.nickName)
            userLocalDataSource.saveUserProfileImg(it.profileImage)
        }.onFailure {
            it.printStackTrace()
        }
    }

    override suspend fun getMyInfo(): User {
        val name = userLocalDataSource.userNameFlow.firstOrNull().orEmpty().ifBlank { "나" }
        val image = userLocalDataSource.userProfileImgFlow.firstOrNull().orEmpty()

        return User(
            id = 0,
            nickName = name,
            relationName = "나",
            profileImage = image
        )
    }

    override suspend fun getMyInviteCode(): String {
        val localCode = userLocalDataSource.inviteCodeFlow.firstOrNull()

        return if (!localCode.isNullOrEmpty()) {
            localCode
        } else {
            val result =
                userRetrofitDataSource.getMyInviteCode().toResult(transform = { it.inviteCode })
            result.fold(
                onSuccess = { inviteCode ->
                    userLocalDataSource.saveInviteCode(inviteCode)
                    inviteCode
                },
                onFailure = {
                    it.printStackTrace()
                    ""
                }
            )
        }
    }

    override suspend fun getUserInfoByInviteCode(inviteCode: String): Result<UserInfo> {
        return userRetrofitDataSource.getUserInfoByInviteCode(inviteCode).toResult(
            transform = { it.toUserInfo() }
        )
    }
}