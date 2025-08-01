package com.pillsquad.yakssok.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiWrappedResponse<T>(
    val code: Int,
    val message: String,
    val body: T? = null
)

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String
)