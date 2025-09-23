package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserInfo

interface FriendRepository {

    suspend fun getFollowingList(): Result<List<User>>
    suspend fun getFollowerList(): Result<List<User>>
    suspend fun getFeedbackTargetList(): Result<List<FeedbackTarget>>
    suspend fun postAddFriend(inviteCode: String): Result<UserInfo>
}