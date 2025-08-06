package com.pillsquad.yakssok.core.domain.repository

interface ImageRepository {

    suspend fun putImage(imageUrl: String): Result<String>
    suspend fun postImage(imageUrl: String): Result<String>
    suspend fun deleteImage(): Result<Unit>
    suspend fun changeImageUrlToFile(uri: String): String?
}