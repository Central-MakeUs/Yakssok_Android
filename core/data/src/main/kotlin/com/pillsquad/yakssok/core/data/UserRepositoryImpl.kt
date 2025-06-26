package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUser
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.di.FakeDataSource
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    @FakeDataSource private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun searchUser(gymName: String): Result<User> {
        return userDataSource.searchUser(gymName).toResult(
            transform = { it.toUser() }
        )
    }
}