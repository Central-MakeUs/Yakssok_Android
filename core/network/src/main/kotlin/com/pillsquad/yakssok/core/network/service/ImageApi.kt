package com.pillsquad.yakssok.core.network.service

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageApi {

    @Multipart
    @PUT("/api/images")
    suspend fun putImage(
        @Query("type") type: String,
        @Query("oldImageUrl") oldImageUrl: String,
        @Part file: MultipartBody.Part
    ): ApiResponse<ImageResponse>

    @Multipart
    @POST("/api/images")
    suspend fun postImage(
        @Query("type") type: String,
        @Part file: MultipartBody.Part
    ): ApiResponse<ImageResponse>

    @DELETE("/api/images")
    suspend fun deleteImage(
        @Query("imageUrl") imageUrl: String
    ): ApiResponse<Unit>

}