package com.pillsquad.yakssok.core.network.remote

import android.content.Context
import androidx.core.net.toUri
import com.pillsquad.yakssok.core.network.datasource.ImageDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.response.ImageResponse
import com.pillsquad.yakssok.core.network.service.ImageApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ImageRetrofitDataSource @Inject constructor(
    private val context: Context,
    private val imageApi: ImageApi
) : ImageDataSource {
    override suspend fun putImage(
        oldImageUrl: String,
        newImageUrl: String
    ): ApiResponse<ImageResponse> {
        val multipartBody = changeMultiPart(newImageUrl)
            ?: return ApiResponse.Failure.NetworkError(IOException("MultipartBody is null"))

        return imageApi.putImage("profile", oldImageUrl, multipartBody)
    }

    override suspend fun postImage(
        newImageUrl: String
    ): ApiResponse<ImageResponse> {
        val multipartBody = changeMultiPart(newImageUrl)
            ?: return ApiResponse.Failure.NetworkError(IOException("MultipartBody is null"))

        return imageApi.postImage("profile", multipartBody)
    }

    override suspend fun deleteImage(imageUrl: String): ApiResponse<Unit> {
        return imageApi.deleteImage(imageUrl)
    }

    private suspend fun changeMultiPart(imageUrl: String): MultipartBody.Part? =
        withContext(Dispatchers.IO) {
            val path = imageUrl.toUri().path ?: return@withContext null
            val file = File(path)
            if (!file.exists()) return@withContext null

            // ✅ 확장자 확인
            val allowedExt = listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")
            val ext = file.extension.lowercase()

            if (ext !in allowedExt) {
                throw IOException("지원하지 않는 파일 타입입니다. ($ext)")
            }

            MultipartBody.Part.createFormData(
                name = "file",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }
}