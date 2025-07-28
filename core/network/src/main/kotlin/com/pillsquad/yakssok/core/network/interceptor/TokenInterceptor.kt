package com.pillsquad.yakssok.core.network.interceptor

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.RefreshRequest
import com.pillsquad.yakssok.core.network.service.TokenApi
import kotlinx.coroutines.runBlocking
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
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            val token = tokenProvider.getAccessToken()
            if (!token.isNullOrBlank()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()

        val response = chain.proceed(newRequest)

        when (response.code) {
            HttpURLConnection.HTTP_OK -> {
                val newAccessToken = response.header("Authorization", null) ?: return response
                val oldAccessToken = tokenProvider.getAccessToken()

                if (newAccessToken != oldAccessToken) {
                    tokenProvider.setAccessToken(newAccessToken)
                }
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val retryRequest = chain.request().newBuilder().apply {
                    runBlocking {
                        tokenProvider.getRefreshToken()?.let {
                            val params = RefreshRequest(it)
                            val newToken = tokenApi.refreshToken(params = params)

                            if (newToken is ApiResponse.Success) {
                                addHeader("Authorization", "Bearer ${newToken.data.accessToken}")
                                tokenProvider.setAccessToken(newToken.data.accessToken)
                            }
                        }
                    }
                }

                return chain.proceed(retryRequest.build())
            }
        }

        return response
    }
}