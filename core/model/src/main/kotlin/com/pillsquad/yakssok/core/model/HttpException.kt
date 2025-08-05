package com.pillsquad.yakssok.core.model

data class HttpException(val code: Long, override val message: String) : Throwable(message)