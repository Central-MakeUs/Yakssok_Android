package com.pillsquad.yakssok.core.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.ImageRepository
import com.pillsquad.yakssok.core.network.datasource.ImageDataSource
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val imageDataSource: ImageDataSource,
    private val localDataSource: UserLocalDataSource
) : ImageRepository {
    override suspend fun putImage(imageUrl: String): Result<String> {
        val oldImageUrl = localDataSource.userProfileImgFlow.firstOrNull()
            ?: return Result.failure(Exception("No image url"))

        return imageDataSource.putImage(oldImageUrl, imageUrl).toResult(
            transform = { it.imageUrl }
        )
    }

    override suspend fun postImage(imageUrl: String): Result<String> {
        return imageDataSource.postImage(imageUrl).toResult(
            transform = { it.imageUrl }
        )
    }

    override suspend fun deleteImage(): Result<Unit> {
        val oldImageUrl = localDataSource.userProfileImgFlow.firstOrNull()
            ?: return Result.failure(Exception("No image url"))

        val result = imageDataSource.deleteImage(oldImageUrl).toResult(
            transform = { it }
        )

        result.onSuccess {
            localDataSource.saveUserProfileImg("")
        }

        return result
    }

    override suspend fun changeImageUrlToFile(uri: String): String? = withContext(Dispatchers.IO) {
        try {
            val byteArray =
                context.contentResolver.openInputStream(uri.toUri())?.use { it.readBytes() }
                    ?: return@withContext null

            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

            options.inSampleSize = calculateInSampleSize(options.outWidth, options.outHeight)
            options.inJustDecodeBounds = false

            val decodedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
                ?: return@withContext null

            val exifOrientation = ExifInterface(ByteArrayInputStream(byteArray)).getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
            )
            val rotatedBitmap = rotateBitmap(decodedBitmap, exifOrientation)

            val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.webp")
            FileOutputStream(file).use { outputStream ->
                rotatedBitmap.compress(Bitmap.CompressFormat.WEBP, 60, outputStream)
            }

            Uri.fromFile(file).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateInSampleSize(width: Int, height: Int): Int {
        val imageSize = 524

        var inSampleSize = 1
        val maxSize = maxOf(width, height)
        val minSize = minOf(width, height)

        if (maxSize > imageSize || minSize > imageSize) {
            val halfMax = maxSize / 2
            val halfMin = minSize / 2

            while (halfMax / inSampleSize >= imageSize && halfMin / inSampleSize >= imageSize) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private suspend fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap =
        withContext(Dispatchers.Default) {
            val angle = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
            if (angle == 0f) bitmap

            val matrix = Matrix().apply { postRotate(angle) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
}