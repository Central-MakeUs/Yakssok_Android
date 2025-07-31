package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.RoutineResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutineApi {

    @GET("/api/medication-schedules")
    suspend fun getMyRoutine(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): ApiResponse<RoutineResponse>

    @GET("/api/medication-schedules/friends/{friendsId}")
    suspend fun getFriendRoutine(
        @Path("friendsId") friendsId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): ApiResponse<RoutineResponse>

    @PUT("/api/medication-schedules/{scheduleId}/take")
    suspend fun putTakeRoutine(
        @Path("scheduleId") scheduleId: Int
    ): ApiResponse<Unit>
}