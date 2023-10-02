package com.example.planet.core.storage.local.room.common

sealed class StateRoom<out T : Any> {

    data class Success<out T : Any>(val data: T) : StateRoom<T>()
    data class Error(val exception: Exception) : StateRoom<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}