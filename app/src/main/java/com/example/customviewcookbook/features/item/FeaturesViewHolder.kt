package com.example.customviewcookbook.features.item

import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.databinding.FeaturesCardItemBinding

internal class FeaturesViewHolder(
    private val binding: FeaturesCardItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feature: Feature, listener: FeaturesAdapter.FeatureClickListener) {
        binding.root.setOnClickListener { listener.onFeatureClick(feature.activity) }

        val featureDetails = feature.featureDetails
        binding.banner.title = featureDetails.name
        binding.banner.description = featureDetails.description
        binding.banner.hasRightButton = true
    }
}
