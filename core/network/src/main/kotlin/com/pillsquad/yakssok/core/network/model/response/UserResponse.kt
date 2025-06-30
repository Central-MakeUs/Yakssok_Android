package com.pillsquad.yakssok.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val name: String
)
