package com.pillsquad.yakssok.core.network.datasource

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.ImageResponse

interface ImageDataSource {
    suspend fun putImage(oldImageUrl: String, newImageUrl: String): ApiResponse<ImageResponse>
    suspend fun postImage(newImageUrl: String): ApiResponse<ImageResponse>
    suspend fun deleteImage(imageUrl: String): ApiResponse<Unit>
}