package ru.santaev.techtask.feature.user.ui.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import ru.santaev.techtask.databinding.ListItemUserBinding
import ru.santaev.techtask.feature.user.ui.entity.UserUiEntity
import ru.santaev.techtask.utils.ListAdapter

class UserAdapter(
    private val userLongClickListener: (Long) -> Unit
) : ListAdapter<UserUiEntity>(UserListDiffUtil()) {

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

    private inner class UserViewHolder(
        private val binding: ListItemUserBinding
    ) : ViewHolder<UserUiEntity>(binding.root) {

        override fun bind(item: UserUiEntity) {
            binding.item = item
            binding.root.setOnLongClickListener {
                userLongClickListener.invoke(item.id)
                true
            }
            binding.executePendingBindings()
        }
    }
}

private class UserListDiffUtil : DiffUtil.ItemCallback<UserUiEntity>() {
    override fun areItemsTheSame(oldItem: UserUiEntity, newItem: UserUiEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserUiEntity, newItem: UserUiEntity): Boolean {
        return oldItem == newItem
    }
}
