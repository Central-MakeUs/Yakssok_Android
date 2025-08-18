package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mapper.toFeedBackTarget
import com.pillsquad.yakssok.core.data.mapper.toFollowUser
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUser
import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.network.datasource.FriendDataSource
import com.pillsquad.yakssok.core.network.model.request.FollowRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendDataSource: FriendDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : FriendRepository {
    override suspend fun getFollowingList(
        isHome: Boolean,
    ): Result<List<User>> {
        // Todo: Local Data와 연동

        return friendDataSource.getFollowingList().toResult { response ->
            response.followingInfoResponses.map {
                if (isHome) {
                    it.toUser()
                } else {
                    it.toFollowUser()
                }
            }
        }
    }

    override suspend fun getFollowerList(): Result<List<User>> {
        return friendDataSource.getFollowerList().toResult { response ->
            response.followerInfoResponses.map { it.toUser() }
        }
    }

    override suspend fun getFeedbackTargetList(): Result<List<FeedbackTarget>> {
        return friendDataSource.getFeedbackTargetList().toResult { response ->
            response.feedbackTargetResponseList.map { it.toFeedBackTarget() }
        }
    }

    override suspend fun postAddFriend(
        inviteCode: String,
        relationName: String
    ): Result<Unit> {
        val params = FollowRequest(
            inviteCode = inviteCode,
            relationName = relationName
        )

        val result = friendDataSource.postAddFriend(params).toResult()

        result.onSuccess {
            val currentCount = userLocalDataSource.mateCountFlow.firstOrNull() ?: 0
            userLocalDataSource.saveMateCount(currentCount + 1)
        }

        return result
    }
}