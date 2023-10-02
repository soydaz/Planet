package com.example.planet.modules.detail.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.planet.modules.detail.data.response.DetailPlanetResponse

@BindingAdapter("setName")
fun TextView.setName(item: DetailPlanetResponse?) {
    item?.let {
        text = it.name ?: "Sin información"
    }
}

@BindingAdapter("setDiameter")
fun TextView.setDiameter(item: DetailPlanetResponse?) {
    item?.let {
        text = it.diameter ?: "Sin información"
    }
}

@BindingAdapter("setTerrain")
fun TextView.setTerrain(item: DetailPlanetResponse?) {
    item?.let {
        text = it.terrain ?: "Sin información"
    }
}