package com.pillsquad.yakssok.core.network.util

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.BaseResponse
import kotlinx.serialization.json.Json
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
        ApiResponse.Failure.NetworkError(e)
    } catch (e: Exception) {
        ApiResponse.Failure.UnknownApiError(e)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T> parseFailureResponse(
    response: Response<BaseResponse<T>>,
): ApiResponse.Failure {

    val errorStr = response.errorBody()?.string().orEmpty()

    val message = try {
        Json.parseToJsonElement(errorStr).jsonObject["message"]?.jsonPrimitive?.content
            ?: "Unknown error"
    } catch (e: Exception) {
        "파싱 실패: ${e.localizedMessage}"
    }

    return ApiResponse.Failure.HttpError(
        code = response.code().toLong(),
        message = message,
        body = errorStr
    )
}

@Suppress("UNCHECKED_CAST")
private fun <T> parseSuccessResponse(
    response: Response<BaseResponse<T>>
): ApiResponse<T> {
    val base = response.body()

    if (base == null) {
        println("base is null despite successful response")
    }

    println("base: $base")

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