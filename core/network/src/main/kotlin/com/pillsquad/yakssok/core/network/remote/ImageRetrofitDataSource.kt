package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.ImageDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.ImageResponse
import com.pillsquad.yakssok.core.network.service.ImageApi
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRetrofitDataSource @Inject constructor(
    private val imageApi: ImageApi
): ImageDataSource {
    override suspend fun putImage(
        type: String,
        oldImageUrl: String,
        file: MultipartBody.Part
    ): ApiResponse<ImageResponse> {
        return imageApi.putImage(type, oldImageUrl, file)
    }

    override suspend fun postImage(
        type: String,
        file: MultipartBody.Part
    ): ApiResponse<ImageResponse> {
        return imageApi.postImage(type, file)
    }

    override suspend fun deleteImage(imageUrl: String): ApiResponse<Unit> {
        return imageApi.deleteImage(imageUrl)
    }
}