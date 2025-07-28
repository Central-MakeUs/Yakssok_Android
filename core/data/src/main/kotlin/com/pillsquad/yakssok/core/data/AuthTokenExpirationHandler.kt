package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.network.interceptor.TokenExpirationHandler
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthTokenExpirationHandler @Inject constructor(
    private val userDataSource: UserLocalDataSource
) : TokenExpirationHandler {
    override fun handleRefreshTokenExpiration() {
        CoroutineScope(Dispatchers.IO).launch {
            userDataSource.clearTokens()
        }
    }
}