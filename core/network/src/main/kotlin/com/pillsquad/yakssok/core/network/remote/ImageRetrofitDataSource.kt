package com.pillsquad.yakssok.core.network.remote

import com.pillsquad.yakssok.core.network.datasource.ImageDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.ImageResponse
import com.pillsquad.yakssok.core.network.service.ImageApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRetrofitDataSource @Inject constructor(
    private val imageApi: ImageApi
) : ImageDataSource {
    override suspend fun putImage(
        oldImageUrl: String,
        newImageUrl: String
    ): ApiResponse<ImageResponse> {
        val multipartBody = changeMultiPart(newImageUrl)

        return imageApi.putImage("profile", oldImageUrl, multipartBody)
    }

    override suspend fun postImage(
        newImageUrl: String
    ): ApiResponse<ImageResponse> {
        val multipartBody = changeMultiPart(newImageUrl)

        return imageApi.postImage("profile", multipartBody)
    }

    override suspend fun deleteImage(imageUrl: String): ApiResponse<Unit> {
        return imageApi.deleteImage(imageUrl)
    }

    private suspend fun changeMultiPart(imageUrl: String): MultipartBody.Part =
        withContext(Dispatchers.IO) {
            val file = File(imageUrl)
            MultipartBody.Part.createFormData(
                name = "file",
                filename = file.name,
                body = file.asRequestBody()
            )
        }
}