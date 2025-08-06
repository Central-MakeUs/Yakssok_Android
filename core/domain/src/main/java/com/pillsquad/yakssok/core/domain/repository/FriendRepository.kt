package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User

interface FriendRepository {

    suspend fun getFollowingList(isHome: Boolean = true): Result<List<User>>
    suspend fun getFollowerList(): Result<List<User>>
    suspend fun getFeedbackTargetList(): Result<List<FeedbackTarget>>
    suspend fun postAddFriend(inviteCode: String, relationName: String): Result<Unit>
}