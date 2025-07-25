package com.pillsquad.yakssok.core.network.util

import android.util.Log
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.BaseResponse
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okio.IOException
import retrofit2.Response

internal fun <T> Response<BaseResponse<T>>.asApiResponse(): ApiResponse<T> {
    return try {
        if (isSuccessful) {
            parseSuccessResponse(this)
        } else {
            parseFailureResponse(this)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        ApiResponse.Failure.NetworkError(e)
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResponse.Failure.UnknownApiError(e)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T> parseFailureResponse(
    response: Response<BaseResponse<T>>,
): ApiResponse.Failure {
    val errorStr = response.errorBody()?.string().orEmpty()

    return try {
        val errorResponse = Json.decodeFromString(BaseResponse.serializer(JsonElement.serializer()), errorStr)

        val code = errorResponse.code
        val message = errorResponse.message

        ApiResponse.Failure.HttpError(
            code = code,
            message = message,
            body = errorResponse.body?.toString() ?: "null"
        )
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResponse.Failure.HttpError(
            code = response.code().toLong(),
            message = "파싱 실패: ${e.localizedMessage}",
            body = errorStr
        )
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T> parseSuccessResponse(
    response: Response<BaseResponse<T>>
): ApiResponse<T> {
    val base = response.body()

    if (base == null) {
        println("base is null despite successful response")
    }

    if (base?.code == 0L) {
        return if (base.body != null) {
            println("success body: ${base.body}")
            ApiResponse.Success(base.body)
        } else {
            println("body is null, returning Unit")
            ApiResponse.Success(Unit as T)
        }
    }

    return ApiResponse.Failure.HttpError(
        code = base?.code ?: response.code().toLong(),
        message = base?.message ?: response.message(),
        body = "null"
    )
}