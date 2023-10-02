package com.example.planet.core.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.awaitResponse
import java.net.ConnectException

abstract class BaseWSManager(private val baseDefinition: BaseWSDefinition) {

    private var mWSCallback: WSCallback? = null
    private var mBaseResponseCall: Call<ResponseBody>? = null
    private var mErrorString = ""
    private var serviceKey = ""

    fun <R : WSBaseResponseInterface> createAnonymous(): AnonymousPresenter<R> {
        return AnonymousPresenter<R>(baseDefinition, this).settings()
    }

    fun settings(wsCallback: WSCallback): BaseWSManager {
        mWSCallback = wsCallback
        return this
    }

    suspend fun <R : WSBaseResponseInterface> requestWs(tClass: Class<R>, webServiceKey: String, call: Call<ResponseBody>): BaseWSManager {
        serviceKey = webServiceKey
        //if (ConnectionUtils.isConnected()) { //&& ConnectionUtils.hasConnectionToInternet()
            mWSCallback!!.onRequestWS(webServiceKey)
            mBaseResponseCall = call

            if (mBaseResponseCall!!.isExecuted)
                mBaseResponseCall!!.cancel()

            try {
                kotlin.runCatching {
                    val responseLocal = mBaseResponseCall!!.awaitResponse()
                    val errorBodyLocal = responseLocal.errorBody()?.string()
                    try {
                        if (responseLocal.isSuccessful) {
                            if (responseLocal.body() != null) {
                                val json = responseLocal.body()!!.string()
                                val gson = Gson()
                                var response: R? = null
                                try {
                                    response = gson.fromJson(json, tClass)
                                } catch (exception: Exception) {
                                    exception.printStackTrace()
                                }
                                if (response != null) {
                                    mWSCallback!!.onSuccessLoadResponse(webServiceKey, response)
                                } else {
                                    mWSCallback!!.onErrorLoadResponse(webServiceKey, "Error al convertir el json")
                                }
                            } else {
                                mWSCallback!!.onErrorLoadResponse(
                                    webServiceKey,
                                    "Without response from ws"
                                )
                            }
                        } else {
                            mErrorString = errorBodyLocal ?: "Service error"
                            mWSCallback!!.onErrorLoadResponse(webServiceKey, mErrorString)
                        }
                        AnonymousPresenter.STATUS_CODE = responseLocal.code()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        mErrorString = e.toString()
                        if (!call.isCanceled) {
                            mWSCallback!!.onErrorLoadResponse(webServiceKey, mErrorString)
                        }
                    }
                }.onFailure {
                    mWSCallback!!.onTimeOutException(webServiceKey)
                }
            } catch (e: ConnectException) {
                mWSCallback!!.onErrorConnection()
            }

        /*} else {
            mWSCallback!!.onErrorConnection()
        }
        */
        return this
    }

}
