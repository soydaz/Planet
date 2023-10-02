package com.example.planet.modules.detail.data.contract

import com.example.planet.modules.detail.data.response.DetailPlanetResponse

class DetailContract {

    interface PresenterInterface {
        fun getDetailPlanetById(id: String)
        fun cleanUp()
    }

    interface ViewInterface {
        fun detailPlanet(planet: DetailPlanetResponse)
        fun notifyError(message: String)
    }

}