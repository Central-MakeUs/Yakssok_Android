package com.pillsquad.yakssok.core.firebase

interface FcmTokenProvider {
    suspend fun fetchFcmTokenOrNull(): String?
}