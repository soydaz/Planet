package com.example.planet.modules.catalogue.data.model

import com.example.planet.core.network.WSBaseResponseInterface
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.Exception

class Planet(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("url")
    var url: String? = ""
) : WSBaseResponseInterface, Serializable {
    var id: Int = 0

    fun getPlanetId(): String {
        var result = ""
        try {
            url?.let {
                var url = it
                url = url.replace("https://swapi.dev/api/planets/","")
                url = url.replace("/","")
                result = url
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return result
    }
}