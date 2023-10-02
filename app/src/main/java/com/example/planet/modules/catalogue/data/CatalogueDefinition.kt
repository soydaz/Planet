package com.example.planet.modules.catalogue.data

import com.example.planet.core.network.BaseWSDefinition
import com.example.planet.core.network.BaseWSManager
import com.example.planet.core.network.ServiceDefinition
import com.example.planet.core.network.WSBaseRequestInterface
import com.example.planet.core.network.WebServices
import com.example.planet.modules.catalogue.data.response.PlanetsResponse
import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.Call

object CatalogueDefinition: BaseWSDefinition {

    const val GET_PLANETS = "getPlanets"

    private interface Services {
        @GET("/api/planets/?format=json")
        fun getAllPlanets(): Call<ResponseBody>
    }

    private val services by lazy {
        WebServices.getInstance().create(Services::class.java)
    }

    override fun ws(key: String, request: WSBaseRequestInterface?): ServiceDefinition<*>? {
        when (key) {
            GET_PLANETS -> {
                return ServiceDefinition(PlanetsResponse::class.java, services.getAllPlanets(), key)
            }
        }
        return null
    }

    class WSManager : BaseWSManager(CatalogueDefinition)

}