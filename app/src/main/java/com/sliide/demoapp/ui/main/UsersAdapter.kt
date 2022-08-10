package com.sliide.demoapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sliide.demoapp.R
import com.sliide.demoapp.model.User

class UsersAdapter(val deleteUser: (id: Int) -> Unit) :
   ListAdapter<User, BindViewHolder<User>>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name && oldItem.email == newItem.email && oldItem.creationRelativeTime == newItem.creationRelativeTime
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder<User> {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_users_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder<User>, position: Int) {
        val item = currentList[position]
        holder.itemView.setOnLongClickListener {
            deleteUser(item.id)
            return@setOnLongClickListener true
        }
        holder.bind(item)
    }

}

class UserViewHolder(itemView: View) : BindViewHolder<User>(itemView) {
    private val emailTextView = itemView.findViewById<TextView>(R.id.emailTextView)
    private val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
    private val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)

    override fun bind(user: User) {
        nameTextView.text = user.name
        emailTextView.text = user.email
        dateTextView.text = user.creationRelativeTime
    }
}

abstract class BindViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(user: T)
}