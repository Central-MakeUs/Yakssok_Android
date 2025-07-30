package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.User

interface FriendRepository {

    suspend fun getFollowingList(): Result<List<User>>
}