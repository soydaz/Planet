package com.example.planet.modules.detail.data.presenter

import com.example.planet.core.common.Result
import com.example.planet.modules.detail.data.contract.DetailContract
import com.example.planet.modules.detail.data.datasource.DetailDataSource
import com.example.planet.modules.detail.data.request.DetailPlanetRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailPresenter(private var mViewInterface: DetailContract.ViewInterface, private var mDataSource: DetailDataSource) : DetailContract.PresenterInterface {

    private val mScope = CoroutineScope(Dispatchers.Main)

    override fun getDetailPlanetById(id: String) {
        mViewInterface.progressMessage("Consultando el detalle")
        mScope.launch {
            val request = DetailPlanetRequest(id.toInt())
            mDataSource.getDetail(request).apply {
                when (this) {
                    is Result.Request -> {}
                    is Result.Success -> {
                        mViewInterface.detailPlanet(this.data)
                    }
                    is Result.Error -> {
                        mViewInterface.notifyError(this.exception.message ?: "Error")
                    }
                }
                mViewInterface.progressMessage(null)
            }
        }
    }

    override fun cleanUp() {
        mScope.cancel()
    }

}