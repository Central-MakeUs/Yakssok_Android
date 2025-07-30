package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toMyInfo
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRetrofitDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postMyInfoToLocal(): Result<MyInfo> {
        val result = userRetrofitDataSource.getMyInfo().toResult(
            transform = { it.toMyInfo() }
        )

        result.onSuccess {
            userLocalDataSource.saveUserName(it.nickName)
            userLocalDataSource.saveUserProfileImg(it.profileImage)
        }

        return result
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
}