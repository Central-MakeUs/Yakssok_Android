package com.pillsquad.yakssok.core.data.mapper

import android.util.Log
import com.pillsquad.yakssok.core.network.model.ApiResponse

inline fun <T, R> ApiResponse<T>.toResult(
    transform: (T) -> R,
    // TODO: Error Code에 따른 action 처리 ex)410이면 자동 로그아웃
    codeActionMap: Map<Long, () -> Unit> = emptyMap()
): Result<R> {
    when (this) {
        is ApiResponse.Success -> {
            try {
                val data = transform(this.data)
                return Result.success(data)
            } catch (throwable: Throwable) {
                return Result.failure(throwable)
            }
        }
        is ApiResponse.Failure.HttpError -> {
            codeActionMap[code]?.invoke()

            return Result.failure(Throwable("Http: $code: $message"))
        }
        is ApiResponse.Failure.NetworkError ->
            return Result.failure(throwable)
        is ApiResponse.Failure.UnknownApiError ->
            return Result.failure(throwable)
    }
}

fun <T> ApiResponse<T>.toResultForLogin(): Result<Boolean> {
    return when (this) {
        is ApiResponse.Success -> {
            Log.d("Login", "Success")
            Result.success(true)
        }
        is ApiResponse.Failure.HttpError -> {
            Log.e("Login", "HttpError: $code: $message")
            if (code == 3000L) {
                Result.success(false)
            } else {
                Result.failure(Throwable("Http: $code: $message"))
            }
        }
        is ApiResponse.Failure.NetworkError -> {
            Log.e("Login", "NetworkError: $throwable")
            Result.failure(throwable)
        }
        is ApiResponse.Failure.UnknownApiError -> {
            Log.e("Login", "NetworkError: $throwable")
            Result.failure(throwable)
        }
    }
}