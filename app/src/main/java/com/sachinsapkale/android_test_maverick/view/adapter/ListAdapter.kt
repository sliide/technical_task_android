package com.android_test_maverick.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_test_maverick.UserModel
import com.sachinsapkale.android_test_maverick.BR
import com.sachinsapkale.android_test_maverick.databinding.AdapterListBinding


class ListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var userlist = mutableListOf<UserModel>()
    private lateinit var listener: ListAdapterListener

    fun setUser(userl: List<UserModel>) {
        this.userlist = userl.toMutableList()
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

        val user = userlist[position]
        holder.bind(user,listener)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    interface ListAdapterListener{
        fun onUserDelete(user: UserModel): Boolean // adding a return type becoz onclick from xml requires a return type
    }
}

class MainViewHolder(val binding: AdapterListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(obj: UserModel,listener: ListAdapter.ListAdapterListener) {
        binding.setVariable(BR.itemViewModel,obj)
        binding.executePendingBindings()
        binding.containerParent.setOnLongClickListener { listener.onUserDelete(obj) }
    }
}