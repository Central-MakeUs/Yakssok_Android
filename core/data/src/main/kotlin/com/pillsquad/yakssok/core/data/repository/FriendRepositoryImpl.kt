package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toFeedBackTarget
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUser
import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.datasource.FriendDataSource
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendDataSource: FriendDataSource,
) : FriendRepository {
    override suspend fun getFollowingList(): Result<List<User>> {
        // Todo: Local Data와 연동

        return friendDataSource.getFollowingList().toResult(
            transform = { response ->
                response.followingInfoResponses.map { it.toUser() }
            }
        )
    }

    override suspend fun getFeedbackTargetList(): Result<List<FeedbackTarget>> {
        return friendDataSource.getFeedbackTargetList().toResult(
            transform = { response ->
                response.feedbackTargetResponseList.map { it.toFeedBackTarget() }
            }
        )
    }
}