package com.example.planet.core.network

import okhttp3.ResponseBody
import retrofit2.Call

data class ServiceDefinition<R : WSBaseResponseInterface>(val tClass: Class<R>, val call: Call<ResponseBody>, var key: String = "")

interface BaseWSDefinition {
    fun ws(key: String, request: WSBaseRequestInterface?): ServiceDefinition<*>?
}