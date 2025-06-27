package com.pillsquad.yakssok.datasource_api.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val code: Int
)
