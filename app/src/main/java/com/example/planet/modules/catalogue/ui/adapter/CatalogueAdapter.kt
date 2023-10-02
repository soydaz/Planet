package com.example.planet.modules.catalogue.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.planet.databinding.LayoutItemPlanetBinding
import com.example.planet.modules.catalogue.data.model.Planet

class CatalogueAdapter(private val mEventListener: EventListener): ListAdapter<Planet, CatalogueAdapter.ItemViewHolder>(CatalogueDiffCallback()) {

    class EventListener(private val listener: (item: Planet) -> Unit) {
        fun onClickListener(planet: Planet) = listener(planet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), mEventListener)
    }

    class ItemViewHolder private constructor(private val binding: LayoutItemPlanetBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup) = ItemViewHolder(LayoutItemPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        fun bind(item: Planet, eventListener: EventListener) = with (item) {
            binding.item = this
            binding.listener = eventListener
            binding.executePendingBindings()
        }

    }

    class CatalogueDiffCallback : DiffUtil.ItemCallback<Planet>() {

        override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem.id == newItem.id
        }

    }

}