package com.pillsquad.yakssok.core.data

import com.pillsquad.yakssok.core.data.mapper.toMyInfo
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.data.mapper.toResultForLogin
import com.pillsquad.yakssok.core.domain.repository.UserRepository
import com.pillsquad.yakssok.core.model.MyInfo
import com.pillsquad.yakssok.core.network.datasource.AuthDataSource
import com.pillsquad.yakssok.core.network.datasource.UserDataSource
import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.request.JoinRequest
import com.pillsquad.yakssok.core.network.model.request.LoginRequest
import com.pillsquad.yakssok.datastore.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authRetrofitDataSource: AuthDataSource,
    private val userRetrofitDataSource: UserDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun joinUser(
        accessToken: String,
        nickName: String,
        pushAgreement: Boolean
    ): Result<Unit> {
        val params = JoinRequest(
            oauthAuthorizationCode = accessToken,
            oauthType = "kakao",
            nonce = "",
            nickName = nickName,
            pushAgreement = pushAgreement,
            fcmToken = ""
        )

        return authRetrofitDataSource.joinUser(params = params).toResult(transform = { it })
    }

    override suspend fun loginUser(accessToken: String): Result<Boolean> {
        val params = LoginRequest(
            oauthAuthorizationCode = accessToken,
            oauthType = "kakao"
        )

        val response = authRetrofitDataSource.loginUser(params = params)

        if (response is ApiResponse.Success) {
            userLocalDataSource.saveAccessToken(response.data.accessToken)
            userLocalDataSource.saveRefreshToken(response.data.refreshToken)
        }

//        getMyInfo().onFailure {
//            it.printStackTrace()
//            Log.e("UserRepositoryImpl", "loginUser: $it")
//        }

        return response.toResultForLogin()
    }

    override suspend fun getMyInfo(): Result<MyInfo> {
        val result =  userRetrofitDataSource.getMyInfo().toResult(
            transform = { it.toMyInfo() }
        )

        result.onSuccess {
            userLocalDataSource.saveUserName(it.nickName)
            userLocalDataSource.saveUserProfileImg(it.profileImage)
        }

        return result
    }

    override fun checkToken(): Flow<Boolean> {
        return combine(
            userLocalDataSource.accessTokenFlow,
            userLocalDataSource.refreshTokenFlow
        ) { accessToken, refreshToken ->
            accessToken.isNotBlank() && refreshToken.isNotBlank()
        }
    }
}