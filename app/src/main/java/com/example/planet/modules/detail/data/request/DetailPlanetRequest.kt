package com.example.planet.modules.detail.data.request

import com.example.planet.core.network.WSBaseRequestInterface
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DetailPlanetRequest(
    @SerializedName("id")
    var planetId: Int? = 0,
) : WSBaseRequestInterface, Serializable