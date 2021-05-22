package com.coolcats.localinterestsapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coolcats.localinterestsapplication.R
import com.coolcats.localinterestsapplication.model.PlacesResponse
import com.coolcats.localinterestsapplication.model.Result
import kotlinx.android.synthetic.main.business_item_layout.view.*

class PlacesAdapter(private var placesList: List<Result>): RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    inner class PlacesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    fun updatePlaces(placesList: List<Result>){
        this.placesList = placesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.business_item_layout, parent, false)
        return PlacesViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        placesList[position].let {
            holder.itemView.apply {
                business_name_textview.text = it.name
                business_address_textview.text = it.vicinity
            }

        }
    }

    override fun getItemCount(): Int = placesList.size

}