package com.hanna.sliidetest.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hanna.sliidetest.R
import com.hanna.sliidetest.models.User

class UsersAdapter : ListAdapter<User, BindableViewHolder<User>>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                 return oldItem.name == newItem.name && oldItem.email == newItem.email && oldItem.creationTime == newItem.creationTime
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<User> {
        return UserInfoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_user_item, parent, false))
    }

    override fun onBindViewHolder(holder: BindableViewHolder<User>, position: Int) {
        holder.bind(currentList[position])
    }
}