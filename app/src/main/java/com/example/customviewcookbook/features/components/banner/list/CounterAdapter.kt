package com.example.customviewcookbook.features.components.banner.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.databinding.CounterItemBinding

internal class CounterAdapter(
    private val listener: CounterItemListener
) : RecyclerView.Adapter<CounterItemViewHolder>() {

    private var items: List<String> = emptyList()

    /**
     * Submit a new list of items to the adapter.
     */
    fun submitList(newItems: List<String>) {
        items = newItems
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CounterItemViewHolder {
        val binding = CounterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return CounterItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CounterItemViewHolder, position: Int) {
        holder.bind(
                item = items[position],
                listener = listener
        )
    }

    override fun getItemCount(): Int = items.size

    /**
     * Listener for item click events.
     */
    internal interface CounterItemListener {

        /**
         * Called when an item is clicked.
         */
        fun onCounterItemClick(item: String)
    }
}
