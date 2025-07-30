package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.FeedbackTargetListResponse
import com.pillsquad.yakssok.core.network.model.response.FollowingsResponse

interface FriendDataSource {

    suspend fun getFollowingList(): ApiResponse<FollowingsResponse>
    suspend fun getFeedbackTargetList(): ApiResponse<FeedbackTargetListResponse>
}