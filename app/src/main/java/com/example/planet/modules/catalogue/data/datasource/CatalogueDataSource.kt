package com.example.planet.modules.catalogue.data.datasource

import com.example.planet.core.common.Result
import com.example.planet.core.network.AnonymousPresenter
import com.example.planet.modules.catalogue.data.CatalogueDefinition
import com.example.planet.modules.catalogue.data.response.PlanetsResponse
import java.io.IOException

class CatalogueDataSource {

    suspend fun getPlanets(): Result<PlanetsResponse> {
        var result: Result<PlanetsResponse> = Result.Request(null)
        CatalogueDefinition.WSManager().createAnonymous<PlanetsResponse>().requestWs(CatalogueDefinition.GET_PLANETS, request = null) { status, response ->
            when (status) {
                AnonymousPresenter.SUCCESS -> result = Result.Success(response!!)
                AnonymousPresenter.ERROR, AnonymousPresenter.NETWORK_ERROR -> result = Result.Error(IOException(AnonymousPresenter.ERROR_MESSAGE))
            }
        }
        return result
    }

}