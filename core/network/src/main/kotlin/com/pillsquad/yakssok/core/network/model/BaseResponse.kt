package com.pillsquad.yakssok.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val code: Long,
    val message: String,
    val body: T? = null
)
