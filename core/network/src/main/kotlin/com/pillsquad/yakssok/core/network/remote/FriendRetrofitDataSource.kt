package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.FriendDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.FeedbackTargetListResponse
import com.pillsquad.yakssok.core.network.model.response.FollowingsResponse
import com.pillsquad.yakssok.core.network.service.FriendApi
import javax.inject.Inject

class FriendRetrofitDataSource @Inject constructor(
    private val friendApi: FriendApi
): FriendDataSource {
    override suspend fun getFollowingList(): ApiResponse<FollowingsResponse> =
        friendApi.getFollowingList()

    override suspend fun getFeedbackTargetList(): ApiResponse<FeedbackTargetListResponse> =
        friendApi.getFeedbackTargetList()
}