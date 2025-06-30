package com.pillsquad.yakssok.core.network.adapter

import com.pillsquad.yakssok.core.network.model.ApiResponse
import com.pillsquad.yakssok.core.network.model.BaseResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResponseCallAdapter<R>(
    private val resultType: Type,
) : CallAdapter<BaseResponse<R>, Call<ApiResponse<R>>> {
    override fun responseType(): Type =
        ParameterizedTypeImpl(BaseResponse::class.java, arrayOf(resultType))

    override fun adapt(call: Call<BaseResponse<R>>): Call<ApiResponse<R>> =
        ApiResponseCallDelegate(call, resultType)
}

class ParameterizedTypeImpl(
    private val raw: Class<*>,
    private val args: Array<Type>
) : ParameterizedType {
    override fun getRawType(): Type = raw
    override fun getActualTypeArguments(): Array<Type> = args
    override fun getOwnerType(): Type? = null
}