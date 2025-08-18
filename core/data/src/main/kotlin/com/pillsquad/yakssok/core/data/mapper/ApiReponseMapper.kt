package com.pillsquad.yakssok.core.data.mapper

import com.pillsquad.yakssok.core.model.HttpException
import com.pillsquad.yakssok.core.network.model.ApiResponse

inline fun <T, R> ApiResponse<T>.toResult(
    @Suppress("UNCHECKED_CAST") transform: (T) -> R = { it as R },
): Result<R> = when (this) {
    is ApiResponse.Success -> runCatching { transform(data) }
    is ApiResponse.Failure.HttpError -> Result.failure(HttpException(code, message))
    is ApiResponse.Failure.NetworkError -> Result.failure(throwable)
    is ApiResponse.Failure.UnknownApiError -> Result.failure(throwable)
}

fun <T> ApiResponse<T>.toResult(): Result<T> = toResult { it }