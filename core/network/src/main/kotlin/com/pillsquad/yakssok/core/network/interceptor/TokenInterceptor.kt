package com.pillsquad.yakssok.core.network.interceptor

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.RefreshRequest
import com.pillsquad.yakssok.core.network.service.TokenApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val tokenApi: TokenApi,
    private val tokenProvider: TokenProvider
) : Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenProvider.getAccessToken()
        val authedRequest = originalRequest.newBuilder().apply {
            if (!accessToken.isNullOrBlank()) header("Authorization", "Bearer $accessToken")
        }.build()

        val response = chain.proceed(authedRequest)

        if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED) {
            return response
        }

        val newAccess = runBlocking { refreshSafely(accessToken) }

        return if (newAccess.isNullOrBlank()) {
            response
        } else {
            response.close()
            val retried = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newAccess")
                .build()
            chain.proceed(retried)
        }
    }

    private suspend fun refreshSafely(oldAccess: String?): String? {
        return mutex.lockAndGet {
            val latest = tokenProvider.getAccessToken()
            if (!latest.isNullOrBlank() && latest != oldAccess) return@lockAndGet latest

            val rt = tokenProvider.getRefreshToken() ?: return@lockAndGet null
            when (val res = tokenApi.refreshToken(RefreshRequest(rt))) {
                is ApiResponse.Success -> {
                    tokenProvider.setAccessToken(res.data.accessToken)
                    res.data.accessToken
                }
                else -> null
            }
        }
    }

    private suspend inline fun <T> Mutex.lockAndGet(block: () -> T): T {
        lock()
        return try { block() } finally { unlock() }
    }
}