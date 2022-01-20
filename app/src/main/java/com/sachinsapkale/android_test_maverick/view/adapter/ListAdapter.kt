package com.android_test_maverick.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_test_maverick.UserModel
import com.sachinsapkale.android_test_maverick.BR
import com.sachinsapkale.android_test_maverick.databinding.AdapterListBinding


class ListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movieList = mutableListOf<UserModel>()

    fun setMovies(movies: List<UserModel>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterListBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val movie = movieList[position]
//        holder.binding.name.text = movie.user +"|"+movie.tags
        holder.bind(movie)
//        Glide.with(holder.itemView.context).load(movie.previewUrl).into(holder.binding.imageview)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}

class MainViewHolder(val binding: AdapterListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(obj: Any?) {
        binding.setVariable(BR.itemViewModel,obj)
        binding.executePendingBindings()
    }
}