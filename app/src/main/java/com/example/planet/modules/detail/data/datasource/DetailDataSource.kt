package com.example.planet.modules.detail.data.datasource

import com.example.planet.core.common.Result
import com.example.planet.core.network.AnonymousPresenter
import com.example.planet.modules.detail.data.PlanetDefinition
import com.example.planet.modules.detail.data.request.DetailPlanetRequest
import com.example.planet.modules.detail.data.response.DetailPlanetResponse
import java.io.IOException

class DetailDataSource {

    suspend fun getDetail(request: DetailPlanetRequest): Result<DetailPlanetResponse> {
        var result: Result<DetailPlanetResponse> = Result.Request(null)
        PlanetDefinition.WSManager().createAnonymous<DetailPlanetResponse>().requestWs(PlanetDefinition.GET_DETAIL_PLANETS, request) { status, response ->
            when (status) {
                AnonymousPresenter.SUCCESS -> result = Result.Success(response!!)
                AnonymousPresenter.ERROR, AnonymousPresenter.NETWORK_ERROR -> result = Result.Error(IOException(AnonymousPresenter.ERROR_MESSAGE))
            }
        }
        return result
    }

}