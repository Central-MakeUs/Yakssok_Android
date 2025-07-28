package com.pillsquad.yakssok.core.network.interceptor

interface TokenExpirationHandler {
    fun handleRefreshTokenExpiration()
}