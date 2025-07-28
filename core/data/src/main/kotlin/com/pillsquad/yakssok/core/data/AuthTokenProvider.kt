package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.network.interceptor.TokenProvider
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthTokenProvider @Inject constructor(
    private val userDataSource: UserLocalDataSource
) : TokenProvider {
    override fun getAccessToken(): String? = runBlocking {
        userDataSource.accessTokenFlow.firstOrNull()
    }

    override fun getRefreshToken(): String? = runBlocking {
        userDataSource.refreshTokenFlow.firstOrNull()
    }

    override fun setAccessToken(accessToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userDataSource.saveAccessToken(accessToken)
        }
    }
}