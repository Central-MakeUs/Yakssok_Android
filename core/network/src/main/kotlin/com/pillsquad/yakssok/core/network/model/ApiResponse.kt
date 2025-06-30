package com.pillsquad.yakssok.core.network.model

import java.io.IOException

sealed interface ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>

    sealed interface Failure : ApiResponse<Nothing> {
        data class HttpError(val code: Long, val message: String, val body: String) : Failure
        data class NetworkError(val throwable: IOException) : Failure
        data class UnknownApiError(val throwable: Throwable) : Failure
    }
}