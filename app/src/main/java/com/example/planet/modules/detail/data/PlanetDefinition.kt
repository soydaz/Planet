package com.example.planet.modules.detail.data

import com.example.planet.core.network.BaseWSDefinition
import com.example.planet.core.network.BaseWSManager
import com.example.planet.core.network.ServiceDefinition
import com.example.planet.core.network.WSBaseRequestInterface
import com.example.planet.core.network.WebServices
import com.example.planet.modules.detail.data.request.DetailPlanetRequest
import com.example.planet.modules.detail.data.response.DetailPlanetResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

object PlanetDefinition : BaseWSDefinition {

    const val GET_DETAIL_PLANETS = "getDetailPlanets"

    private interface Services {
        @GET("/api/planets/{id}/?format=json")
        fun getDetailById(@Path ("id") id: Int?): Call<ResponseBody>
    }

    private val services by lazy {
        WebServices.getInstance().create(Services::class.java)
    }

    override fun ws(key: String, request: WSBaseRequestInterface?): ServiceDefinition<*>? {
        when (key) {
            GET_DETAIL_PLANETS -> {
                return ServiceDefinition(DetailPlanetResponse::class.java, services.getDetailById((request as DetailPlanetRequest).planetId), key)
            }
        }
        return null
    }

    class WSManager : BaseWSManager(PlanetDefinition)

}