package com.pillsquad.yakssok.core.data.repository

import com.pillsquad.yakssok.core.data.mock.MockDataSource
import com.pillsquad.yakssok.core.data.mapper.toFeedBackTarget
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toUser
import com.pillsquad.yakssok.core.data.mapper.toUserInfo
import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserInfo
import com.pillsquad.yakssok.core.network.datasource.FriendDataSource
import com.pillsquad.yakssok.core.network.model.request.FollowRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendDataSource: FriendDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val mockDataSource: MockDataSource
) : FriendRepository {

    override suspend fun getMateList(): Result<List<User>> = executeOrMock(
        apiCall = {
            friendDataSource.getFollowerList()
                .toResult { it.followerInfoResponses.map { res -> res.toUser() } }
        },
        mockCall = { mockDataSource.getMateList() }
    )

    override suspend fun getFeedbackTargetList(): Result<List<FeedbackTarget>> = executeOrMock(
        apiCall = {
            friendDataSource.getFeedbackTargetList()
                .toResult { it.feedbackTargetResponseList.map { res -> res.toFeedBackTarget() } }
        },
        mockCall = { mockDataSource.getFeedbackTarget() }
    )

    override suspend fun postAddFriend(inviteCode: String): Result<UserInfo> {
        val params = FollowRequest(inviteCode = inviteCode)
        val result = friendDataSource.postAddFriend(params).toResult { it.toUserInfo() }

        return result.onSuccess { userLocalDataSource.incrementMateCount() }
    }

    private suspend fun <T> executeOrMock(
        isMock: suspend () -> Boolean = {
            userLocalDataSource.tutorialCompleteFlow.firstOrNull()?.not() ?: true
        },
        apiCall: suspend () -> Result<T>,
        mockCall: suspend () -> T
    ): Result<T> {
        return if (isMock()) {
            runCatching { mockCall() }
        } else {
            apiCall()
        }
    }
}