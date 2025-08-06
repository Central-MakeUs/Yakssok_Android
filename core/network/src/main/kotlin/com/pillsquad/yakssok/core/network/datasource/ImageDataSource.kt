package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.ImageResponse
import okhttp3.MultipartBody

interface ImageDataSource {
    suspend fun putImage(type: String, oldImageUrl: String, file: MultipartBody.Part): ApiResponse<ImageResponse>
    suspend fun postImage(type: String, file: MultipartBody.Part): ApiResponse<ImageResponse>
    suspend fun deleteImage(imageUrl: String): ApiResponse<Unit>
}