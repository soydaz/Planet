package com.example.planet.modules.catalogue.data.contract

import com.example.planet.modules.catalogue.data.model.Planet

class CatalogueContract {

    interface PresenterInterface {
        fun getPlanetsList()
        fun cleanUp()
    }

    interface ViewInterface {
        fun notifyPlanets(planetsList: ArrayList<Planet>)
        fun progressMessage(message: String?)
        fun notifyError(message: String)
    }

}