package com.example.starwarsplanets.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsplanets.R
import com.example.starwarsplanets.model.PlanetData

class PlanetsAdapter (
    private val c: Context,
    private val planetList: ArrayList<PlanetData>,
    private val onItemClick: (PlanetData) -> Unit

) : RecyclerView.Adapter<PlanetsAdapter.PlanetViewHolder> () {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.planet_list, parent, false)
        return PlanetViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PlanetViewHolder,
        position: Int
    ) {
        val planet = planetList[position]

        holder.planetName.text = planet.planetName
        holder.planetType.text = planet.planetType

        Glide.with(c)
            .load(planet.planetImage)
            .into(holder.planetImage)

        holder.itemView.setOnClickListener {
            onItemClick(planet)
        }
    }

    override fun getItemCount(): Int = planetList.size

    inner class PlanetViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

                val planetImage: ImageView = itemView.findViewById(R.id.planetImage)
                val planetName: TextView = itemView.findViewById(R.id.planetName)
                val planetType: TextView = itemView.findViewById(R.id.planetType)
            }
}