package com.example.planet.modules.catalogue.data.response

import com.example.planet.core.network.WSBaseResponseInterface
import com.example.planet.modules.catalogue.data.model.Planet
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlanetsResponse(
    @SerializedName("count")
    var count: Int? = 0,
    @SerializedName("next")
    var next: String? = "",
    @SerializedName("previous")
    var previous: String? = "",
    @SerializedName("results")
    var resultsList: ArrayList<Planet>? = arrayListOf()
): WSBaseResponseInterface, Serializable