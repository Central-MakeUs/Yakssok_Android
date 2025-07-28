package com.pillsquad.yakssok.core.network.calladapter

import com.pillsquad.yakssok.core.network.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when(getRawType(returnType)) {
           Call::class.java -> {
               val callType = getParameterUpperBound(0, returnType as ParameterizedType)
               val rawType = getRawType(callType)
               if(rawType != ApiResponse::class.java) {
                   return null
               }

               val resultType = getParameterUpperBound(0, callType as ParameterizedType)
               return ApiResponseCallAdapter<Any>(resultType)
           }

            else -> null
        }
    }
}