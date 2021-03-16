package com.devforfun.sliidetask.ui.users

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devforfun.sliidetask.R
import com.devforfun.sliidetask.services.model.User
import com.devforfun.sliidetask.utils.inflate
import kotlinx.android.synthetic.main.user_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class UsersAdapter(private var items: MutableList<User>,
                     private val clickListener: (User) -> Unit)
    : RecyclerView.Adapter<UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(parent.inflate(R.layout.user_list_item))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newData: MutableList<User>) {
        items = newData
        items.sortBy { it.name }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    fun deleteUser(deletedUserId: Int) {
        val user = items.find { it.id == deletedUserId }
        val index = items.indexOf(user)
        items.removeAt(index)
        items.sortBy { it.name }
        notifyItemRemoved(index)
    }

    fun insertUser(user: User) {
        val index = items.size
        items.add(index ,user)
        items.sortBy { it.name }
        notifyItemInserted(index)
    }
}

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userName : TextView = itemView.textViewHead
    private val email : TextView = itemView.texViewSubHead
    private val creationDate = itemView.texViewSubSubHead
    private val time = itemView.textViewLeftItem

    fun bind(objectKind: User, clickListener: (User) -> Unit) {
        itemView.setOnLongClickListener {
            clickListener(objectKind)
            true
        }

        userName.text = objectKind.name
        email.text = objectKind.email
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        var date = dateFormat.parse(dateFormat.format(Date()))
        if(objectKind.createdAt.isNotEmpty()) {
            date = dateFormat.parse(objectKind.createdAt)
        }

        val currentDate = SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault()).format(date)
        val currentTime = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(date)
        creationDate.text = currentDate
        time.text = currentTime
    }
}