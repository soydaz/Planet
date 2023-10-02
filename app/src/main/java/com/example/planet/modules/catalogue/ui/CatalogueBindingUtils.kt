package com.example.planet.modules.catalogue.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.planet.modules.catalogue.data.model.Planet

@BindingAdapter("setNamePlanet")
fun TextView.setNamePlanet(item: Planet?) {
    item?.let {
        text = it.name ?: "Sin información"
    }
}