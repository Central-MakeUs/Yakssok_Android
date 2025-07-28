package com.pillsquad.yakssok.core.network.interceptor

interface TokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun setAccessToken(accessToken: String)
}