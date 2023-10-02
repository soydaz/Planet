package com.example.planet.core.network

class AnonymousPresenter<R : WSBaseResponseInterface>(val wsDefinition: BaseWSDefinition, val wsManager: BaseWSManager) : WSCallback {

    lateinit var mCallback: Callback<R>

    lateinit var mWSManager: BaseWSManager

    var mRequest: WSBaseRequestInterface? = null
    private var mResponse: WSBaseResponseInterface? = null

    companion object {
        const val REQUEST: Int = 1
        const val SUCCESS: Int = 2
        const val ERROR: Int = 3
        const val NETWORK_ERROR: Int = 4
        var ERROR_MESSAGE: String = ""
        var STATUS_CODE: Int = 0
    }

    fun settings(): AnonymousPresenter<R> {
        return this
    }

    interface Callback<R> {
        fun onChangeStatusWS(status: Int, response: R? = null)
    }

    suspend inline fun requestWs(key: String, request: WSBaseRequestInterface?, crossinline listener: (status: Int, response: R?) -> Unit) {
        mCallback = object : Callback<R> {
            override fun onChangeStatusWS(status: Int, response: R?) {
                listener(status, response)
            }
        }
        this.mRequest = request
        val wsDefinition = wsDefinition.ws(key, request)
        mWSManager = wsManager.settings(this)
        mWSManager.requestWs(wsDefinition!!.tClass, key, wsDefinition.call)
    }

    override fun onRequestWS(requestUrl: String) {
        mCallback.onChangeStatusWS(REQUEST)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSuccessLoadResponse(requestUrl: String, baseResponse: WSBaseResponseInterface) {
        mResponse = baseResponse as R
        mCallback.onChangeStatusWS(SUCCESS, baseResponse)
    }

    override fun onErrorLoadResponse(requestUrl: String, messageError: String) {
        ERROR_MESSAGE = messageError
        mCallback.onChangeStatusWS(ERROR)
    }

    override fun onErrorConnection() {
        ERROR_MESSAGE = "No hay conexión a internet"
        mCallback.onChangeStatusWS(NETWORK_ERROR)
    }

    override fun onTimeOutException(webServiceKey: String) {
        ERROR_MESSAGE = webServiceKey.plus(" - Timeout error")
        mCallback.onChangeStatusWS(NETWORK_ERROR)
    }

}