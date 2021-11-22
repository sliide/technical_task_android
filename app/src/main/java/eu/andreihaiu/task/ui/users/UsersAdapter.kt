package eu.andreihaiu.task.ui.users

import androidx.recyclerview.widget.DiffUtil
import eu.andreihaiu.task.R
import eu.andreihaiu.task.data.models.Users
import eu.andreihaiu.task.databinding.CellUsersItemBinding
import eu.andreihaiu.task.ui.base.DataBoundListAdapter
import eu.andreihaiu.task.ui.base.DataBoundViewHolder


class UsersAdapter(
    private val onDeleteCallback: ((Users) -> Unit)? = null
) : DataBoundListAdapter<Users, CellUsersItemBinding>(
    UsersDiffUtil
) {
    override fun getItemLayout(): Int = R.layout.cell_users_item

    override fun addClickListeners(holder: DataBoundViewHolder<CellUsersItemBinding>) {
        holder.binding.clItem.setOnLongClickListener {
            onDeleteCallback?.invoke(getItem(holder.bindingAdapterPosition))
            true
        }
    }

    override fun bind(
        holder: DataBoundViewHolder<CellUsersItemBinding>,
        item: Users
    ) {
        holder.binding.item = item
    }

    companion object {
        object UsersDiffUtil : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name &&
                        oldItem.email == newItem.email && oldItem.gender == newItem.gender
            }
        }
    }
}