package com.android_test_maverick.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_test_maverick.UserModel
import com.sachinsapkale.android_test_maverick.BR
import com.sachinsapkale.android_test_maverick.databinding.AdapterListBinding


class ListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movieList = mutableListOf<UserModel>()
    private lateinit var listener: ListAdapterListener

    fun setUser(movies: List<UserModel>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    fun setListener(lstnr: ListAdapterListener) {
        this.listener = lstnr
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterListBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    interface ListAdapterListener{
        fun onUserDelete(user: UserModel)
    }
}

class MainViewHolder(val binding: AdapterListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(obj: Any?) {
        binding.setVariable(BR.itemViewModel,obj)
        binding.executePendingBindings()
    }
}