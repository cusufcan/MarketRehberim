package com.cusufcan.marketrehberim.common

sealed class Resource<T>(
    val value: T? = null,
    val message: String? = null,
) {
    class Idle<T> : Resource<T>()
    class Success<T>(value: T) : Resource<T>(value)
    class Error<T>(message: String) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()
}