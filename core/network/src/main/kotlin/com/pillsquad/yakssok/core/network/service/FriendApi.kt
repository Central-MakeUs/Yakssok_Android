package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.FollowRequest
import com.pillsquad.yakssok.core.network.model.response.FeedbackTargetListResponse
import com.pillsquad.yakssok.core.network.model.response.FollowerResponse
import com.pillsquad.yakssok.core.network.model.response.FollowingsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FriendApi {

    @GET("/api/friends/followings")
    suspend fun getFollowingList(): ApiResponse<FollowingsResponse>

    @GET("/api/friends/followers")
    suspend fun getFollowerList(): ApiResponse<FollowerResponse>

    @GET("/api/friends/medication-status")
    suspend fun getFeedbackTargetList(): ApiResponse<FeedbackTargetListResponse>

    @POST("/api/friends")
    suspend fun postAddFriend(
        @Body params: FollowRequest
    ): ApiResponse<Unit>
}