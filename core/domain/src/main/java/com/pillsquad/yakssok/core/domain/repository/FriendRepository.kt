package com.pillsquad.yakssok.core.domain.repository

import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User

interface FriendRepository {

    suspend fun getFollowingList(): Result<List<User>>
    suspend fun getFeedbackTargetList(): Result<List<FeedbackTarget>>
}