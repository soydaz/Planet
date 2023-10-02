package com.example.planet.modules.detail.data.response

import com.example.planet.core.network.WSBaseResponseInterface
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DetailPlanetResponse(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("diameter")
    var diameter: String? = "",
    @SerializedName("terrain")
    var terrain: String? = ""
): WSBaseResponseInterface, Serializable