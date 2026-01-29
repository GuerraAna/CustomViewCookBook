package com.example.customviewcookbook.features.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.customviewcookbook.databinding.FeaturesCardItemBinding

internal class FeaturesAdapter(
    val listener: FeatureClickListener
) : ListAdapter<Feature, FeaturesViewHolder>(FeatureDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeaturesViewHolder {
        val binding = FeaturesCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return FeaturesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FeaturesViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), listener)
    }

    private class FeatureDiffCallback : DiffUtil.ItemCallback<Feature>() {
        override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem.name == newItem.name
        }
    }

    internal interface FeatureClickListener {
        fun onFeatureClick(feature: Class<out AppCompatActivity>)
    }
}
