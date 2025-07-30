package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.FeedbackTargetListResponse
import com.pillsquad.yakssok.core.network.model.response.FollowingsResponse
import retrofit2.http.GET

interface FriendApi {

    @GET("/api/friends/followings")
    suspend fun getFollowingList(): ApiResponse<FollowingsResponse>

    @GET("/api/friends/medication-status")
    suspend fun getFeedbackTargetList(): ApiResponse<FeedbackTargetListResponse>
}