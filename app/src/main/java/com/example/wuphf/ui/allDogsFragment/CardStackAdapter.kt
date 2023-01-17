package com.example.wuphf.ui.allDogsFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wuphf.R
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class CardStackAdapter(val context: Context): RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    private var spots: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image)
            .load(spots.get(position))
            .into(holder.image)

        holder.image.scaleType = ImageView.ScaleType.FIT_XY

        holder.itemView.setOnClickListener { v ->
        }
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: List<String>){
        spots = data
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}