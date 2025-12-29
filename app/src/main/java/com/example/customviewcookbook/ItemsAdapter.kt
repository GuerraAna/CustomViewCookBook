package com.example.customviewcookbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.databinding.ItemDataBinding

internal class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.DataViewHolder>() {

    private var items: List<String> = emptyList()

    fun submitList(newItems: List<String>) {
        items = newItems
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val binding = ItemDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class DataViewHolder(
        private val binding: ItemDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvItemName.text = item
        }
    }
}
