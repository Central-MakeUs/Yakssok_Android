package com.pillsquad.yakssok.datasource_api.model

sealed interface ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>

    sealed interface Failure : ApiResponse<Nothing> {
        data class HttpError(val code: Int, val message: String, val body: String) : Failure
        data class NetworkError(val throwable: Throwable) : Failure
        data class UnknownApiError(val throwable: Throwable) : Failure
    }
}

inline fun <A, B> ApiResponse<A>.map(transform: (A) -> B): ApiResponse<B> =
    when (this) {
        is ApiResponse.Success -> ApiResponse.Success(transform(data))
        is ApiResponse.Failure -> this
    }