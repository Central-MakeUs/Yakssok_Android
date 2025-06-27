package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUser
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(
    @param:Named("FakeUser") private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserByGymName(gymName: String): Result<User> {
        return userDataSource.searchUser(gymName).toResult(
            transform = { it.toUser() }
        )
    }
}