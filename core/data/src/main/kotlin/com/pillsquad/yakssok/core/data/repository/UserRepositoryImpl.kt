package com.pillsquad.yakssok.core.data.repository

import android.util.Log
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUserInfo
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserInfo
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.request.MyInfoRequest
import com.pillsquad.yakssok.core.network.model.request.UserInitialRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userRetrofitDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postMyInfoToLocal(): Result<Unit> {
        val result = userRetrofitDataSource.getMyInfo().toResult(transform = { it })

        return result.onSuccess {
            userLocalDataSource.saveInfo(
                it.nickName,
                it.profileImage ?: "",
                it.medicationCount,
                it.followingCount
            )
        }.onFailure {
            it.printStackTrace()
            Log.e("UserRepositoryImpl", "postMyInfoToLocal: $it")
        }.mapCatching { }
    }

    override suspend fun getMyUser(): User {
        val name = userLocalDataSource.userNameFlow.firstOrNull().orEmpty().ifBlank { "나" }
        val image = userLocalDataSource.userProfileImgFlow.firstOrNull().orEmpty()

        return User(
            id = 0,
            nickName = name,
            relationName = "나",
            profileImage = image
        )
    }

    override suspend fun putMyInfo(
        nickName: String,
        profileImage: String
    ): Result<Unit> {
        val params = MyInfoRequest(
            nickname = nickName,
            profileImageUrl = profileImage
        )

        val result = userRetrofitDataSource.putMyInfo(params).toResult(transform = { it })

        result.onSuccess {
            userLocalDataSource.saveUserName(nickName)
            userLocalDataSource.saveUserProfileImg(profileImage)
        }.onFailure {
            it.printStackTrace()
            Log.e("UserRepositoryImpl", "putMyInfo: $it")
        }

        return result
    }

    override suspend fun putUserInitial(nickName: String): Result<Unit> {
        val params = UserInitialRequest(nickName)
        val result = userRetrofitDataSource.putUserInitial(params).toResult(transform = { it })

        result.onSuccess {
            userLocalDataSource.saveUserName(nickName)
            userLocalDataSource.saveInitialized(true)
        }.onFailure {
            it.printStackTrace()
            Log.e("UserRepositoryImpl", "putUserInitial: $it")
        }

        return result
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

    override suspend fun getMyInfo(): Flow<MyInfo> {
        return combine(
            userLocalDataSource.userNameFlow,
            userLocalDataSource.userProfileImgFlow,
            userLocalDataSource.medicationCountFlow,
            userLocalDataSource.mateCountFlow,
            userLocalDataSource.pushAgreementFlow
        ) { nickName, profileImage, medicationCount, mateCount, pushAgreement ->
            MyInfo(
                nickName = nickName,
                profileImage = profileImage,
                medicationCount = medicationCount,
                followingCount = mateCount,
                isAgreement = pushAgreement
            )
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        val result = userRetrofitDataSource.deleteUser().toResult(transform = { it })

        result.onSuccess {
            userLocalDataSource.clearAllData()
            // Todo: room 데이터 삭제
        }.onFailure {
            it.printStackTrace()
            Log.e("UserRepositoryImpl", "deleteUser: $it")
        }

        return result
    }
}