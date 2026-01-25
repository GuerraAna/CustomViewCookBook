package com.example.customviewcookbook.features.components.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.databinding.CounterItemBinding

internal class CounterAdapter : RecyclerView.Adapter<CounterItemViewHolder>() {

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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
