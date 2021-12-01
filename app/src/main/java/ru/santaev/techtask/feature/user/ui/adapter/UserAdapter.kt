package ru.santaev.techtask.feature.user.ui.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import ru.santaev.techtask.databinding.ListItemUserBinding
import ru.santaev.techtask.network.entities.UserEntity
import ru.santaev.techtask.utils.ListAdapter

class UserAdapter : ListAdapter<UserEntity>(UserListDiffUtil()) {

    init {
        registerView(
            matcher = { true },
            viewHolderCreator = { parent ->
                UserViewHolder(
                    ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        )
    }

    private class UserViewHolder(
        private val binding: ListItemUserBinding
    ) : ViewHolder<UserEntity>(binding.root) {

        override fun bind(item: UserEntity) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}

private class UserListDiffUtil : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem == newItem
    }
}
