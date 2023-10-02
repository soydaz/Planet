package com.example.planet.core.common

import com.google.gson.annotations.SerializedName

sealed class Result<out T : Any> {

    data class Success<out T : Any>(@SerializedName("data")  val data: T) : Result<T>()
    data class Error(@SerializedName("exception")  val exception: Exception) : Result<Nothing>()
    data class Request(@SerializedName("any") val any: Nothing?) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=${exception.message}]"
            is Request -> "Request"
        }
    }
}