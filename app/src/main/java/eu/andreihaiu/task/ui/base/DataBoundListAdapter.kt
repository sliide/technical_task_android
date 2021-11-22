package eu.andreihaiu.task.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import java.util.concurrent.Executors

abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val holder = DataBoundViewHolder(createBinding(parent))
        addClickListeners(holder)
        return holder
    }

    private fun createBinding(parent: ViewGroup): V = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        getItemLayout(),
        parent,
        false
    )

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun getItemLayout(): Int
    protected abstract fun addClickListeners(holder: DataBoundViewHolder<V>)
    protected abstract fun bind(holder: DataBoundViewHolder<V>, item: T)
}