package com.example.customviewcookbook.features.components.banner

import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.databinding.CounterItemBinding

internal class CounterItemViewHolder(
    private val binding: CounterItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind the item to the view.
     */
    fun bind(item: String) {
        binding.tvItemName.text = item
    }
}
