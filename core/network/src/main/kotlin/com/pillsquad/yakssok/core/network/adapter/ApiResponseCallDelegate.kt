package com.pillsquad.yakssok.core.network.adapter

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.BaseResponse
import com.pillsquad.yakssok.core.network.util.asApiResponse
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class ApiResponseCallDelegate<T>(
    private val call: Call<BaseResponse<T>>,
    private val resultType: Type
) : Call<ApiResponse<T>> {

    override fun enqueue(callback: Callback<ApiResponse<T>>) = synchronized(this) {
        call.enqueue(object : Callback<BaseResponse<T>> {
            override fun onResponse(
                call: Call<BaseResponse<T>>,
                response: Response<BaseResponse<T>>
            ) {
                val apiResponse = response.asApiResponse()
                callback.onResponse(this@ApiResponseCallDelegate, Response.success(apiResponse))
            }

            override fun onFailure(
                call: Call<BaseResponse<T>>,
                t: Throwable
            ) {
                val error = if (t is IOException) {
                    ApiResponse.Failure.NetworkError(t)
                } else {
                    ApiResponse.Failure.UnknownApiError(t)
                }
                callback.onResponse(this@ApiResponseCallDelegate, Response.success(error))
            }
        })
    }

    override fun execute(): Response<ApiResponse<T>> = runBlocking {
        val response = call.execute()
        val apiResponse = response.asApiResponse()
        Response.success(apiResponse)
    }

    override fun clone(): Call<ApiResponse<T>> = ApiResponseCallDelegate(call.clone(), resultType)
    override fun cancel() = call.cancel()
    override fun request(): Request = call.request()
    override fun timeout(): Timeout = call.timeout()

    override fun isExecuted(): Boolean = call.isExecuted
    override fun isCanceled(): Boolean = call.isCanceled
}
