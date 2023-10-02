package com.example.planet.modules.catalogue.data.presenter

import com.example.planet.core.common.Result
import com.example.planet.modules.catalogue.data.contract.CatalogueContract
import com.example.planet.modules.catalogue.data.datasource.CatalogueDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


/**
 *
 * @author Cesar Aparicio
 * @since 1.0
 *
 */

class CataloguePresenter(private var mViewInterface: CatalogueContract.ViewInterface, private var mDataSource: CatalogueDataSource) : CatalogueContract.PresenterInterface {

    private val mScope = CoroutineScope(Dispatchers.Main)

    override fun getPlanetsList() {
        mViewInterface.progressMessage("Consultando planetas")
        mScope.launch {
            mDataSource.getPlanets().apply {
                when (this) {
                    is Result.Request -> {}
                    is Result.Success -> {
                        this.data.resultsList?.let { list ->
                            if (list.isNotEmpty())
                                mViewInterface.notifyPlanets(list)
                            else
                                mViewInterface.notifyError("No hay datos")
                        }
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