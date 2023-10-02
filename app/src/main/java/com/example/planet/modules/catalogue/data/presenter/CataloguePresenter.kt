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

    private val mPlanetsImageUrl = arrayListOf(
        "https://images.pexels.com/photos/39561/solar-flare-sun-eruption-energy-39561.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        "https://images.pexels.com/photos/87651/earth-blue-planet-globe-planet-87651.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        "https://images.pexels.com/photos/5476413/pexels-photo-5476413.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        "https://images.pexels.com/photos/5304000/pexels-photo-5304000.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")

    override fun getPlanetsList() {
        mViewInterface.progressMessage("Consultando planetas")
        mScope.launch {
            mDataSource.getPlanets().apply {
                when (this) {
                    is Result.Request -> {}
                    is Result.Success -> {
                        val planetsList = this.data.resultsList ?: arrayListOf()
                        for (planet in planetsList) {
                            planet.image = mPlanetsImageUrl.random()
                        }
                        planetsList.let { list ->
                            if (list.isNotEmpty())
                                mViewInterface.notifyPlanets(list)
                            else
                                mViewInterface.notifyError("No hay datos sobre planetas")
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