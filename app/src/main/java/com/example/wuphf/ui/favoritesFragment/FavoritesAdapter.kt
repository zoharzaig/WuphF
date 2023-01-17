package com.example.wuphf.ui.favoritesFragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wuphf.R
import com.example.wuphf.data.local.model.Dog
import com.example.wuphf.databinding.FavoriteItemLayoutBinding
import com.example.wuphf.ui.allDogsFragment.FavoriteListener
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class FavoritesAdapter( private val listener : FavoriteListener) : RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder>() {

    private var favorites:List<Dog> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(favorites[position])

    override fun getItemCount() = favorites.size

    fun submitList(data: List<Dog>){
        favorites = data
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: FavoriteItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener, View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onFavoriteItemClicked(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            listener.onFavoriteItemLongClicked(adapterPosition)
            return true
        }

        fun bind(dog: Dog) {
            Glide.with(binding.favoriteImage.context)
                .load(dog.message)
                .into(binding.favoriteImage)
        }
    }
}