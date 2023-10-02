package com.example.planet.core.network

interface WSCallback {
    fun onRequestWS(requestUrl: String)
    fun onSuccessLoadResponse(requestUrl: String, baseResponse: WSBaseResponseInterface)
    fun onErrorLoadResponse(requestUrl: String, messageError: String)
    fun onErrorConnection()
    fun onTimeOutException(webServiceKey: String)
}